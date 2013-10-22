package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class TrabalhoCientificoModelo extends Model {
   @Id
   public Long id;
   
   @ManyToOne
   public TrabalhoCientifico trabalho_cientifico;
   
   @ManyToOne
   public Modelo modelo;

}
