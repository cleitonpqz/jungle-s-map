package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class MunicipioLocal extends Model {
   @Id
    public Long id;
   
   public Boolean principal;
   
   @ManyToOne
   public Municipio municipio;
   
   @ManyToOne
   public Local local;

}
