package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Equacao extends Model {
   @Id
    public Long id;
    
    @Constraints.Required(message="O campo expressao é obrigatório!")
    public String expressao;
    
    public String variavel_A;
    public Double valor_variavel_A;
    
    public String variavel_B;
    public Double valor_variavel_B;
    
    public String variavel_C;
    public Double valor_variavel_C; 
    
    public String variavel_D;
    public Double valor_variavel_D; 
    
    public String variavel_E;
    public Double valor_variavel_E; 
    
    public String variavel_F;
    public Double valor_variavel_F;
    
    public String variavel_G;
    public Double valor_variavel_G; 
    
    public String variavel_H;
    public Double valor_variavel_H; 
    
    public String variavel_I;
    public Double valor_variavel_I; 
    
    public String variavel_J;
    public Double valor_variavel_J; 
    
    public String variavel_K;
    public Double valor_variavel_K; 
    
    public String variavel_L;
    public Double valor_variavel_L; 
    
    public String variavel_M;
    public Double valor_variavel_M;
        
    public String variavel_N;
    public Double valor_variavel_N;
    
    public String variavel_O;
    public Double valor_variavel_O; 
        
    public String variavel_P;
    public Double valor_variavel_P;
    
    public String variavel_Q;
    public Double valor_variavel_Q;
    
    public String variavel_R;
    public Double valor_variavel_R; 
    
    public String variavel_S;
    public Double valor_variavel_S; 
    
    public String variavel_T;
    public Double valor_variavel_T; 
    
    public String variavel_U;
    public Double valor_variavel_U; 
    
    public String variavel_V;
    public Double valor_variavel_V;
    
    public String variavel_W;
    public Double valor_variavel_W;
        
    public String variavel_X;
    public Double valor_variavel_X; 
    
    public String variavel_Y;
    public Double valor_variavel_Y; 
    
    public String variavel_Z;
    public Double valor_variavel_Z; 
    
    @ManyToOne
    public TrabalhoVariavelInteresse trabalho_variavel_interesse;
            
    
    public static Model.Finder<Long,Equacao> find = new Model.Finder<Long,Equacao>(Long.class, Equacao.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Equacao e: Equacao.find.orderBy("expressao").findList()) {
            opcoes.put(e.id.toString(), e.expressao);
         }
        return opcoes;
    }
    
    public static Page<Equacao> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("expressao", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
