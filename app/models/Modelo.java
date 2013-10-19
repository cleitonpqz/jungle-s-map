package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Modelo extends Model {
   @Id
    public Long id;
    
    @Constraints.Required(message="O campo expressao é obrigatório!")
    public String expressao;
    
    @Constraints.Required(message="O campo expressao é obrigatório!")
    public Integer ano;
    
    @ManyToOne
    public AutorModelo autor_modelo;
    
    @ManyToOne
    public TrabalhoVariavelInteresse trabalho_variavel_interesse;
            
    
    public static Model.Finder<Long,Modelo> find = new Model.Finder<Long,Modelo>(Long.class, Modelo.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Modelo m: Modelo.find.orderBy("ano").findList()) {
            opcoes.put(m.id.toString(), m.expressao +"("+m.autor_modelo.nome+")");
         }
        return opcoes;
    }
    
    public static Page<Modelo> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("ano", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
