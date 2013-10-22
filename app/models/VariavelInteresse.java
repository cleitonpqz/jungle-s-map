package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class VariavelInteresse extends Model {
   @Id
    public Long id;
   
    @Constraints.Required(message="O campo sigla é obrigatório!")
    public String sigla;
    
    @Constraints.Required(message="O campo nome é obrigatório!")
    public String nome;
    
    public static Model.Finder<Long,VariavelInteresse> find = new Model.Finder<Long,VariavelInteresse>(Long.class, VariavelInteresse.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(VariavelInteresse v: VariavelInteresse.find.orderBy("nome").findList()) {
            opcoes.put(v.id.toString(), v.nome +" ("+v.sigla+")");
         }
        return opcoes;
    }
    
    public static Page<VariavelInteresse> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("nome", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
