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
    
    @Constraints.Required(message="O campo modelo é obrigatório!")
    public String expressao;
    
    public Integer qtd_coeficientes;
    
    @ManyToOne
    public AutorModelo autor_modelo;
    
    @ManyToOne
    public VariavelInteresse variavel_interesse;
    
    @OneToMany(targetEntity = Termo.class, cascade = CascadeType.ALL)
    public List<Termo> termos = new ArrayList<Termo>();
    
    @OneToMany(targetEntity = ModeloVariavel.class, cascade = CascadeType.ALL)
    public List<ModeloVariavel> modelo_variavel = new ArrayList<ModeloVariavel>();  
    
    @OneToMany(targetEntity = TrabalhoCientificoModelo.class, cascade = CascadeType.ALL)
    public List<TrabalhoCientificoModelo> trabalho_cientifico_modelo = new ArrayList<TrabalhoCientificoModelo>();            
    
    public static Model.Finder<Long,Modelo> find = new Model.Finder<Long,Modelo>(Long.class, Modelo.class);

    public static Map<String,String> opcoes(long id) {
        
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Modelo m: Modelo.find.where().eq("variavel_interesse.id", id).orderBy("expressao").findList()) {
            opcoes.put(m.id.toString(),m.autor_modelo.nome+" "+m.expressao);
        }
        return opcoes;
    }
    
    public static Page<Modelo> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("expressao", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
