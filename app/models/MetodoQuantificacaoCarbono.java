package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class MetodoQuantificacaoCarbono extends Model {
   @Id
    public Long id;
    
    @Constraints.Required(message="O campo Metodo de Quantificação de Carbono é obrigatório!")
    public String descricao;
    
    public static Model.Finder<Long,MetodoQuantificacaoCarbono> find = new Model.Finder<Long,MetodoQuantificacaoCarbono>(Long.class, MetodoQuantificacaoCarbono.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(MetodoQuantificacaoCarbono m: MetodoQuantificacaoCarbono.find.orderBy("descricao").findList()) {
            opcoes.put(m.id.toString(), m.descricao);
         }
        return opcoes;
    }
    
    public static List<MetodoQuantificacaoCarbono> list(String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("descricao", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findList();
    }
}
