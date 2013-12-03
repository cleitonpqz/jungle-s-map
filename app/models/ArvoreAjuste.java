package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class ArvoreAjuste extends Model {
   @Id
    public Long id;

    public Long num_arvore;
    public Double qtd_biomassa_obs;
    public Double qtd_carbono_obs;
    public Double qtd_carbono_est;
    public Double qtd_volume_obs;
    public Double qtd_volume_est;
    public Double qtd_biomassa_est;
    
    @OneToMany(targetEntity = ArvoreAjusteVariavel.class, cascade = CascadeType.ALL)
    public List<ArvoreAjusteVariavel> arvore_ajuste_variavel= new ArrayList<ArvoreAjusteVariavel>();
    
   
    
}