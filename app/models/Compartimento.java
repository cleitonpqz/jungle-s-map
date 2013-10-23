package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Compartimento extends Model {
   @Id
    public Long id;
    
    @Constraints.Required(message="O campo descrição é obrigatório!")
    public String descricao;
    
    public static Model.Finder<Long,Compartimento> find = new Model.Finder<Long,Compartimento>(Long.class, Compartimento.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Compartimento c: Compartimento.find.orderBy("descricao").findList()) {
            opcoes.put(c.id.toString(), c.descricao);
         }
        return opcoes;
    }
    
    public static Page<Compartimento> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("descricao", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
