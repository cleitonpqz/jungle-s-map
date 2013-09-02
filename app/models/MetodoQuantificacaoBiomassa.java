package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class MetodoQuantificacaoBiomassa extends Model {
   @Id
    public Long id;
    
    @Constraints.Required(message="O campo Metodo de Quantificação de Biomassa é obrigatório!")
    public String descricao;
    
    public static Model.Finder<Long,MetodoQuantificacaoBiomassa> find = new Model.Finder<Long,MetodoQuantificacaoBiomassa>(Long.class, MetodoQuantificacaoBiomassa.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(MetodoQuantificacaoBiomassa m: MetodoQuantificacaoBiomassa.find.orderBy("descricao").findList()) {
            opcoes.put(m.id.toString(), m.descricao);
         }
        return opcoes;
    }
    
    public static List<MetodoQuantificacaoBiomassa> list(String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("descricao", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findList();
    }
}
