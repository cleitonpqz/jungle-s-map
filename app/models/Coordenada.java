package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Coordenada extends Model {
   @Id
    public Long id;
   
    @Constraints.Required(message="O campo latitude é obrigatório!")
    public Double latitude;
    
    @Constraints.Required(message="O campo longitude é obrigatório!")
    public Double longitude;
    
    @ManyToOne
    @Constraints.Required(message="O campo Local é obrigatório!")
    @Formats.NonEmpty
    public Local local;
    
     public static Model.Finder<Long,Coordenada> find = new Model.Finder<Long,Coordenada>(Long.class, Coordenada.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Coordenada c: Coordenada.find.orderBy("id").findList()) {
            String opcao = Double.toString(c.latitude)+" "+Double.toString(c.longitude);
            opcoes.put(c.id.toString(), opcao);
         }
        return opcoes;
    }
    
    public static Page<Coordenada> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("id", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
