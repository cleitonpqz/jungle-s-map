package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;

@Entity 
public class ModeloVariavel extends Model {
   
    @Id
    public Long id;
   
   public Double valor;
   
   @ManyToOne
   public Modelo modelo;
   
   @ManyToOne
   public Variavel variavel;

}
