package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Autor extends Model {
   @Id
    public Long id;
    
    @Constraints.Required(message="O campo autor é obrigatório!")
    public String nome;
    
    public static Model.Finder<Long,Autor> find = new Model.Finder<Long,Autor>(Long.class, Autor.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Autor a: Autor.find.orderBy("nome").findList()) {
            opcoes.put(a.id.toString(), a.nome);
         }
        return opcoes;
    }
    
    public static Page<Autor> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("nome", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
