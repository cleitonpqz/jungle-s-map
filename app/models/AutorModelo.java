package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class AutorModelo extends Model {
   @Id
    public Long id;
    
    @Constraints.Required(message="O campo autor é obrigatório!")
    public String nome;
    
    public static Model.Finder<Long,AutorModelo> find = new Model.Finder<Long,AutorModelo>(Long.class, AutorModelo.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(AutorModelo a: AutorModelo.find.orderBy("nome").findList()) {
            opcoes.put(a.id.toString(), a.nome);
         }
        return opcoes;
    }
    
    public static Page<AutorModelo> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("nome", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
