package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Pais extends Model {
   @Id
    public Long id;
   
    @Constraints.Required(message="O campo nome é obrigatório!")
    public String nome;
    
    public static Model.Finder<Long,Pais> find = new Model.Finder<Long,Pais>(Long.class, Pais.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Pais p: Pais.find.orderBy("nome").findList()) {
            opcoes.put(p.id.toString(), p.nome);
         }
        return opcoes;
    }
    
    public static Page<Pais> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("nome", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
