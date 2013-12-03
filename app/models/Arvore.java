package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Arvore extends Model {
   
   @Id
    public Long id;

    @ManyToOne
    public Parcela parcela;

<<<<<<< HEAD
    @ManyToMany(cascade=CascadeType.ALL)
    public Local local;

    public Long numArvore;
    public String qtdBiomassaObs;
    public String qtdCarbonoObs;
    public Long qtdCarbonoEst;
    public String qtdVolumeObs;
    public Long qtdVolumeEst;
    public Long qtdBiomassaEst;

    @OneToMany(targetEntity = VariavelArvore.class, cascade = CascadeType.ALL)
    public List<VariavelArvore> variavelArvore = new ArrayList<VariavelArvore>();

=======
    public Long num_arvore;
    public Double qtd_biomassa_obs;
    public Double qtd_carbono_obs;
    public Double qtd_carbono_est;
    public Double qtd_volume_obs;
    public Double qtd_volume_est;
    public Double qtd_biomassa_est;

    @OneToMany(targetEntity = VariavelArvore.class, cascade = CascadeType.ALL)
    public List<VariavelArvore> variavel_arvore = new ArrayList<VariavelArvore>();
>>>>>>> Cálculos
    
    public static Model.Finder<Long,Arvore> find = new Model.Finder<Long,Arvore>(Long.class, Arvore.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Arvore a: Arvore.find.orderBy("num_arvore").findList()) {
            opcoes.put(a.id.toString(), a.num_arvore.toString()) ;
         }
        return opcoes;
    }
    
    public static Page<Arvore> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("numArvore", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
