package models;

import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;

@Entity 
public class Variavel extends Model {
   @Id
    public Long id;
   
    @Constraints.Required(message="O campo sigla é obrigatório!")
    public String sigla;
    
    @Constraints.Required(message="O campo nome é obrigatório!")
    public String nome;

    @OneToMany(targetEntity = VariavelArvore.class, cascade = CascadeType.ALL)
    public VariavelArvore variavel_arvore;
    
    public static Model.Finder<Long,Variavel> find = new Model.Finder<Long,Variavel>(Long.class, Variavel.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Variavel v: Variavel.find.orderBy("nome").findList()) {
            opcoes.put(v.id.toString()+","+v.sigla, v.nome +" ("+v.sigla+")");
         }
        return opcoes;
    }
    
    public static List<Variavel> listar(){
        return Variavel.find.orderBy("id").findList();
    }
    
    public static Page<Variavel> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("nome", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }

    public static List<SqlRow> findByLocal(Long id) {
         
        String sql =" SELECT DISTINCT " 
                      +"variavel.id," 
                      +"variavel.sigla," 
                      +"variavel.nome "
                    +" FROM "
                      +"public.variavel," 
                      +"public.local, "
                      +"public.trabalho_cientifico, "
                      +"public.trabalho_cientifico_equacao," 
                      +"public.equacao," 
                      +"public.equacao_variavel"
                    +" WHERE "
                      +"local.trabalho_cientifico_id = trabalho_cientifico.id AND " 
                      +"trabalho_cientifico.id = trabalho_cientifico_equacao.trabalho_cientifico_id AND "
                      +"trabalho_cientifico_equacao.equacao_id = equacao.id AND "
                      +"equacao.id = equacao_variavel.equacao_id AND "
                      +"equacao_variavel.variavel_id = variavel.id AND "
                      +"local.id = '" + id + "'";

        
         SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
         List<SqlRow> rows = sqlQuery.findList();

        
        return rows;
  }

  public static List<Variavel> _findByLocal(long id){
    List<Variavel> variaveis = 
    Ebean.find(Variavel.class)
      .fetch("Local.trabalho_cientifico_id")
      .fetch("TrabalhoCientifico.id")
      .fetch("equacaovariavel.equacao_id")
      .fetch("Equacao.trabalho_cientifico_equacao")
      .fetch("TrabalhoCientificoEquacao.trabalho_cientifico_id")
      
      
      .where().eq("local.id", id)
      .findList();

      return variaveis;
  }
}
