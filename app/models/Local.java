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
public List<Coordenada> coordenadas = new ArrayList<Coordenada>();

public static Model.Finder<Long,Local> find = new Model.Finder<Long,Local>(Long.class, Local.class);

public static List<Local> list(String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("descricao", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findList();
    }
public static Page<Local> localizarSimilar(int page, int pageSize, String sortBy, String order, int bioma, int formacao, String local, int estado, int municipio) {
         if(formacao!=-1){
            if(municipio!=-1){
                return find.where()
                .ilike("descricao", "%" + local + "%")
                .eq("formacao.id", formacao)
                .eq("municipios_locais.municipio.id", municipio)
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
            }else if(estado!=-1){
                        return find.where()
                        .ilike("descricao", "%" + local + "%")
                        .eq("formacao.id", formacao)
                        .eq("municipios_locais.municipio.uf.id", estado)
                        .orderBy(sortBy + " " + order)
                        .findPagingList(pageSize)
                        .getPage(page);
                   }else{
                        return find.where()
                        .ilike("descricao", "%" + local + "%")
                        .eq("formacao.id", formacao)
                        .orderBy(sortBy + " " + order)
                        .findPagingList(pageSize)
                        .getPage(page);
                   }
        }else if (bioma!=-1){
                    if (municipio!=-1){
                        return find.where()
                        .ilike("descricao", "%" + local + "%")
                        .eq("formacao.bioma.id", bioma)
                        .eq("municipios_locais.municipio.id", municipio)
                        .orderBy(sortBy + " " + order)
                        .findPagingList(pageSize)
                        .getPage(page);
                    }else if(estado!=-1){
                            return find.where()
                            .ilike("descricao", "%" + local + "%")
                            .eq("formacao.bioma.id", bioma)
                            .eq("municipios_locais.municipio.uf.id", estado)
                            .orderBy(sortBy + " " + order)
                            .findPagingList(pageSize)
                            .getPage(page);
                       }else{
                            return find.where()
                            .ilike("descricao", "%" + local + "%")
                            .eq("formacao.bioma.id", bioma)
                            .orderBy(sortBy + " " + order)
                            .findPagingList(pageSize)
                            .getPage(page);
                       }
            } else if (municipio!=-1){
                        return find.where()
                        .ilike("descricao", "%" + local + "%")
                        .eq("municipios_locais.municipio.id", municipio)
                        .orderBy(sortBy + " " + order)
                        .findPagingList(pageSize)
                        .getPage(page);
                    }else if(estado!=-1){
                            return find.where()
                            .ilike("descricao", "%" + local + "%")
                            .eq("municipios_locais.municipio.uf.id", estado)
                            .orderBy(sortBy + " " + order)
                            .findPagingList(pageSize)
                            .getPage(page);
                       }else{
                            return find.where()
                            .ilike("descricao", "%" + local + "%")
                            .orderBy(sortBy + " " + order)
                            .findPagingList(pageSize)
                            .getPage(page);
                       }
     }

}
