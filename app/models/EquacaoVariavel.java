package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;

@Entity 
public class EquacaoVariavel extends Model {
   
    @Id
    public Double id;
    
    public Double valor;
   
   @ManyToOne
   public Equacao equacao;
   
   @ManyToOne
   public Variavel variavel;

}
