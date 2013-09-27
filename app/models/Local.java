package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Local extends Model {

@Id
public Long id;

@Constraints.Required(message="O campo local é obrigatório!")
public String descricao;

@Constraints.Required(message="O campo área  obrigatório!")
public Float area_total;

@ManyToOne
public TrabalhoCientifico trabalho_cientifico;
 
@ManyToOne
public Formacao formacao;

@ManyToOne
public Espacamento espacamento;

@OneToMany(targetEntity = MunicipioLocal.class, cascade = CascadeType.ALL)
public List<MunicipioLocal> municipios_locais = new ArrayList<MunicipioLocal>();

@OneToMany(targetEntity = Coordenada.class, cascade = CascadeType.ALL)
public List<Coordenada> coordenadas = new ArrayList<Coordenada>();;

public static Model.Finder<Long,Local> find = new Model.Finder<Long,Local>(Long.class, Local.class);

public static List<Local> list(String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("descricao", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findList();
    }

}
