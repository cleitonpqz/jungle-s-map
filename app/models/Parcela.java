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

    @ManyToOne
    public Local local;

    @OneToMany(targetEntity = Arvore.class, cascade = CascadeType.ALL)
    public List<Arvore> arvore = new ArrayList<Arvore>();

    public Long num_parcela;
    public Double area;
    public Double biomassa;
    public Double qtd_biomassa_min;
    public Double qtd_biomassa_med;
    public Double qtd_biomassa_max;
    public Double carbono;
    public Double qtd_carbono_min;
    public Double qtd_carbono_med;
    public Double qtd_arbono_max;
    public Double volume;
    public Double qtd_volume_min;
    public Double qtd_volume_med;
    public Double qtd_volume_max;
    public Double r2;
    public Double r2_ajust;
    public Double ia;
    public Double syx;
    public Double syx_perc;
    public Double fm;
    public Double syx_fm;
    public Double syx_fm_perc;
        
   public static Model.Finder<Long,Parcela> find = new Model.Finder<Long,Parcela>(Long.class, Parcela.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Parcela a: Parcela.find.orderBy("num_parcela").findList()) {
            opcoes.put(a.id.toString(), a.num_parcela.toString()) ;
         }
        return opcoes;
    }
    
}
