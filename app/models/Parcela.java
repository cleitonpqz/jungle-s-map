package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Parcela extends Model {
   @Id
    public Long id;

    public Long numParcela;
    public long area;
    public Long qtdBiomassaMin;
    public Long qtdBiomassaMed;
    public Long qtdBiomassaMax;
    public Long qtdCarbonoMin;
    public Long qtdCarbonoMed;
    public Long qtdCarbonoMax;
    public Long qtdVolumeMin;
    public Long qtdVolumeMed;
    public Long qtdVolumeMax;
    public Long r2;
    public Long r2Ajust;
    public Long ia;
    public Long syx;
    public Long syxPerc;
    public Long fm;
    public Long syxFm;
    public Long syxFmPerc;
        
   public static Model.Finder<Long,Parcela> find = new Model.Finder<Long,Parcela>(Long.class, Parcela.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Parcela a: Parcela.find.orderBy("numParcela").findList()) {
            opcoes.put(a.id.toString(), a.numParcela.toString()) ;
         }
        return opcoes;
    }
    
    public static Page<Parcela> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("numParcela", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
