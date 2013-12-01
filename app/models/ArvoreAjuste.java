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

    public Long numArvore;
    public String qtdBiomassaObs;
    public String qtdCarbonoObs;
    public Long qtdCarbonoEst;
    public String qtdVolumeObs;
    public Long qtdVolumeEst;
    public Long qtdBiomassaEst;
    
    @OneToMany(targetEntity = ArvoreAjusteVariavel.class, cascade = CascadeType.ALL)
    public List<ArvoreAjusteVariavel> arvore_ajuste_variavel= new ArrayList<ArvoreAjusteVariavel>();
    
   
    
}