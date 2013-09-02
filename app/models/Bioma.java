package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Bioma extends Model {
   @Id
    public Long id;
    
    @Constraints.Required(message="O campo bioma é obrigatório!")
    public String nome;
    
    public static Model.Finder<Long,Bioma> find = new Model.Finder<Long,Bioma>(Long.class, Bioma.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Bioma b: Bioma.find.orderBy("nome").findList()) {
            opcoes.put(b.id.toString(), b.nome);
         }
        return opcoes;
    }
    
    public static Page<Bioma> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("nome", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
