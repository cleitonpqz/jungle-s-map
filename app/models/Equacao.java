package models;

import java.util.*;
import javax.persistence.*;
import java.sql.*;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import java.sql.SQLException;

import com.avaje.ebean.*;

@Entity 
public class Equacao extends Model {
   @Id
    public Long id;
    
    @Constraints.Required(message="O campo equação é obrigatório!")
    public String expressao;
    
    @Constraints.Required(message="O campo equação é obrigatório!")
    public String visualizacao;
    
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
    public VariavelInteresse variavel_interesse;
    
    @OneToMany(targetEntity = TrabalhoCientificoEquacao.class, cascade = CascadeType.ALL)
    public List<TrabalhoCientificoEquacao> trabalho_cientifico_equacao = new ArrayList<TrabalhoCientificoEquacao>();  
            
    
    public static Model.Finder<Long,Equacao> find = new Model.Finder<Long,Equacao>(Long.class, Equacao.class);

    public static Map<String,String> opcoes(long id) {
        String sigla;
        switch((int)id){
            case 1: sigla= "B = ";
                    break;
            case 2: sigla= "C = ";
                break;
            case 3: sigla ="V = ";
                break;
            default: sigla="";
        }
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Equacao e: Equacao.find.where().eq("variavel_interesse.id", id).orderBy("visualizacao").findList()) {
            opcoes.put(e.id.toString(), sigla+e.visualizacao);
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
    public static void testaEquacao(String expressao)throws SQLException{
        String sql = "Select "+expressao+" from equacao limit 10";
        Connection conn = play.db.DB.getConnection();
        try {
            Statement stmt = conn.createStatement();
            try {
                stmt.execute(sql);
            } finally {
                stmt.close();
            }
        } finally {
            conn.close();
        }
    }
}
