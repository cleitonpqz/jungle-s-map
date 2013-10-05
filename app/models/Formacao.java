package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Formacao extends Model {
   @Id
    public Long id;
    
    @Constraints.Required(message="O campo formação é obrigatório!")
    public String nome;
    
    
    @ManyToOne
    @Constraints.Required(message="O campo bioma é obrigatório!")
    @Formats.NonEmpty
    public Bioma bioma;
    
    public static Model.Finder<Long,Formacao> find = new Model.Finder<Long,Formacao>(Long.class, Formacao.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Formacao f: Formacao.find.orderBy("nome").findList()) {
            opcoes.put(f.id.toString(), f.nome);
        }
        return opcoes;
    }

    public static List<Formacao> opcoesPorBioma(Long id) {
       if(id!=-1){
       return Formacao.find.where().eq("bioma.id", id).orderBy("nome").findList();
       }else
       return Formacao.find.where().orderBy("nome").findList();
    }
    
    public static Page<Formacao> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("nome", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .fetch("bioma")
                .findPagingList(pageSize)
                .getPage(page);
    }
}
