package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Disponibilidade extends Model {
   @Id
    public Long id;
    
    @Constraints.Required(message="O campo disponibilidade é obrigatório!")
    public String descricao;
    
    public static Model.Finder<Long,Disponibilidade> find = new Model.Finder<Long,Disponibilidade>(Long.class, Disponibilidade.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Disponibilidade d: Disponibilidade.find.orderBy("descricao").findList()) {
            opcoes.put(d.id.toString(), d.descricao);
         }
        return opcoes;
    }
    
    public static Page<Disponibilidade> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("descricao", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
