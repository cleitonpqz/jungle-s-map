package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class VariavelArvore extends Model {
   @Id
    public Long id;

    @ManyToOne
    public Arvore arvore;
    @ManyToOne
    public Variavel variavel;
    public Long valor;
    
   public static Model.Finder<Long,VariavelArvore> find = new Model.Finder<Long,VariavelArvore>(Long.class, VariavelArvore.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(VariavelArvore a: VariavelArvore.find.orderBy("id").findList()) {
            opcoes.put(a.id.toString(), a.valor.toString()) ;
         }
        return opcoes;
    }
    
}
