package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;

@Entity 
public class ArvoreAjusteVariavel extends Model {
   
  @Id
  public Long id;
    
  public Double valor;
   
   @ManyToOne
   public ArvoreAjuste arvore_ajuste;
   
   @ManyToOne
   public Variavel variavel;

}