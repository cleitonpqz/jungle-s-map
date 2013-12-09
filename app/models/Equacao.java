package models;

import java.util.*;
import javax.persistence.*;
import java.sql.*;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import java.sql.SQLException;
import org.nfunk.jep.*;
import com.avaje.ebean.*;


@Entity 
public class Equacao extends Model {
   @Id
    public Long id;
    
    public String expressao;
    
    public String expressao_modelo;
    
    public Integer qtd_coeficientes;
    
    @ManyToOne
    public AutorModelo autor_modelo;
    
    @ManyToOne
    public VariavelInteresse variavel_interesse;
    
    @OneToMany(targetEntity = Termo.class, cascade = CascadeType.ALL)
    public List<Termo> termos = new ArrayList<Termo>();
    
    @OneToMany(targetEntity = EquacaoVariavel.class, cascade = CascadeType.ALL)
    public List<EquacaoVariavel> equacao_variavel = new ArrayList<EquacaoVariavel>();  
    
    @OneToMany(targetEntity = TrabalhoCientificoEquacao.class, cascade = CascadeType.ALL)
    public List<TrabalhoCientificoEquacao> trabalho_cientifico_equacao = new ArrayList<TrabalhoCientificoEquacao>();  
            
    
    public static Model.Finder<Long,Equacao> find = new Model.Finder<Long,Equacao>(Long.class, Equacao.class);

    public static Map<String,String> opcoesEquacao(long id) {
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
        for(Equacao e: Equacao.find.where().eq("variavel_interesse.id", id).orderBy("expressao").findList()) {
            String auxOpcao="";
            if(e.expressao!=null){
                if(e.expressao!=""){
                    auxOpcao = sigla+e.expressao;
                }
            }else{
                auxOpcao="Não possui equação cadastrado.";
            }
             if(e.expressao_modelo!=null){
                 if(e.expressao_modelo!=""){
                 auxOpcao=auxOpcao+" | "+sigla+e.expressao_modelo;
                 }
             }else{
                 auxOpcao= auxOpcao+" | Não possui modelo cadastrado";
             }
                    
               
            
            opcoes.put(e.id.toString(), auxOpcao);
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
    
    public Double calcular(){
        JEP myParser = new org.nfunk.jep.JEP();
         myParser.addStandardFunctions();
         myParser.addStandardConstants();
         for(EquacaoVariavel equacaoVariavel : equacao_variavel)
            myParser.addVariable(equacaoVariavel.variavel.sigla, equacaoVariavel.valor);
         
         myParser.parseExpression(expressao);
         return myParser.getValue();
    }
}
