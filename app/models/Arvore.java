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

    public Long numArvore;
    public long dap;
    public Long altura;
    public String qtdBiomassaObs;
    public String qtdCarbonoObs;
    public Long qtdCarbonoEst;
    public String qtdVolumeObs;
    public Long qtdVolumeEst;
    public Long qtdBiomassaEst;
    
   public static Model.Finder<Long,Arvore> find = new Model.Finder<Long,Arvore>(Long.class, Arvore.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Arvore a: Arvore.find.orderBy("numArvore").findList()) {
            opcoes.put(a.id.toString(), a.numArvore.toString());
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
