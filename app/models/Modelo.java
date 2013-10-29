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
    
    @Constraints.Required(message="O campo modelo é obrigatório!")
    public String visualizacao;
    
    public String variavel_A;
       
    public String variavel_B;
       
    public String variavel_C;
        
    public String variavel_D;
        
    public String variavel_E;
       
    public String variavel_F;
   
    public String variavel_G;
    
    public String variavel_H;
  
    public String variavel_I;
   
    public String variavel_J;
    
    public String variavel_K;
    
    public String variavel_L;
   
    public String variavel_M;
           
    public String variavel_N;
    
    public String variavel_O;
    
    public String variavel_P;
     
    public String variavel_Q;
    
    public String variavel_R;
   
    public String variavel_S;
    
    public String variavel_T;
    
    public String variavel_U;
    
    public String variavel_V;
    
    public String variavel_W;
       
    public String variavel_X;
   
    public String variavel_Y;
    
    public String variavel_Z;
   
    @ManyToOne
    public AutorModelo autor_modelo;
    
    @ManyToOne
    public VariavelInteresse variavel_interesse;
    
    @OneToMany(targetEntity = TrabalhoCientificoModelo.class, cascade = CascadeType.ALL)
    public List<TrabalhoCientificoModelo> trabalho_cientifico_modelo = new ArrayList<TrabalhoCientificoModelo>();            
    
    public static Model.Finder<Long,Modelo> find = new Model.Finder<Long,Modelo>(Long.class, Modelo.class);

    public static Map<String,String> opcoes(long id) {
        
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Modelo m: Modelo.find.where().eq("variavel_interesse.id", id).orderBy("visualizacao").findList()) {
            opcoes.put(m.id.toString(),m.autor_modelo.nome+" "+m.visualizacao);
        }
        return opcoes;
    }
    
    public static Page<Modelo> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("visualizacao", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
