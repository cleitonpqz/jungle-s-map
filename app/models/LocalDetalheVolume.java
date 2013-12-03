package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity
public class LocalDetalheVolume extends Model{
    
    @Id
    public Long id;
    @OneToOne(optional=false)
    @PrimaryKeyJoinColumn
    public Local local;
    
    public Long   numLocal;
    public double qtde_volume_min; //ton    
    public double qtde_volume_med; //ton    
    public double qtde_volume_max; //ton        
    public double media_parcela;
    public double variancia;
    public double desvio_padrao;
    public double variancia_media;
    public double erro_padrao;
    public double coeficiente_variacao;
    public double erro_absoluto;
    public double erro_relativo;
    public double intervalo_confianca_min_parcela;
    public double intervalo_confianca_max_parcela;
    
}