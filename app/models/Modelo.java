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
    public VariavelInteresse variavel_interesse;
    
    @OneToMany(targetEntity = TrabalhoCientificoModelo.class, cascade = CascadeType.ALL)
    public List<TrabalhoCientificoModelo> trabalho_cientifico_modelo = new ArrayList<TrabalhoCientificoModelo>();            
    
    public static Model.Finder<Long,Modelo> find = new Model.Finder<Long,Modelo>(Long.class, Modelo.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Modelo m: Modelo.find.orderBy("autor_modelo.nome").findList()) {
            opcoes.put(m.id.toString(), m.expressao +"("+m.autor_modelo.nome+")");
         }
        return opcoes;
    }
    
     public static Map<String,String> opcoes(long id) {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Modelo m: Modelo.find.where().eq("variavel_interesse.id", id).orderBy("expressao").findList()) {
            opcoes.put(m.id.toString(),m.autor_modelo.nome +" "+ m.expressao);
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
