package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;

@Entity 
public class Termo extends Model {
   
   @Id
   public Double id;
   
   public String expressao;
   
   public Integer ordem;
   
   @ManyToOne
   public Equacao equacao;

}