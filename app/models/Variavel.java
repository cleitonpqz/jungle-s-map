package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Variavel extends Model {
   @Id
    public Long id;
   
    @Constraints.Required(message="O campo sigla é obrigatório!")
    public String sigla;
    
    @Constraints.Required(message="O campo nome é obrigatório!")
    public String nome;
    
    public static Model.Finder<Long,Variavel> find = new Model.Finder<Long,Variavel>(Long.class, Variavel.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Variavel v: Variavel.find.orderBy("nome").findList()) {
            opcoes.put(v.sigla.toString(), v.nome +" ("+v.sigla+")");
         }
        return opcoes;
    }
    
    public static Page<Variavel> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("nome", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
