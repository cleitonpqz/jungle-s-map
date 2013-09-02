package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Espacamento extends Model {
   @Id
    public Long id;
    
    @Constraints.Required(message="O campo espaçamento é obrigatório!")
    public String descricao;
    
    public static Model.Finder<Long,Espacamento> find = new Model.Finder<Long,Espacamento>(Long.class, Espacamento.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Espacamento e: Espacamento.find.orderBy("descricao").findList()) {
            opcoes.put(e.id.toString(), e.descricao);
         }
        return opcoes;
    }
    
    public static Page<Espacamento> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("descricao", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
