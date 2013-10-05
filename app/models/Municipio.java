package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Municipio extends Model {
    
    @Id
    @Column(name="ibge")
    public Long id;
    
    @Constraints.Required(message="O campo Municipio é obrigatório!")
    public String nome;

    @ManyToOne
    @JoinColumn(name="uf")
    @Constraints.Required(message="O campo Estado é obrigatório!")
    public Estado uf;

    @Column(columnDefinition="numeric(15,5)")
    public Double area;

    public static Model.Finder<Long,Municipio> find = new Model.Finder<Long,Municipio>(Long.class, Municipio.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Municipio b: Municipio.find.orderBy("nome").findList()) {
            opcoes.put(b.id.toString(), b.nome);
         }
        return opcoes;
    }
    
    public static Page<Municipio> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("nome", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
    
     public static List<Municipio> opcoesPorEstado(Long id) {
       if(id!=-1){
       return Municipio.find.where().eq("uf.id", id).orderBy("nome").findList();
       }else
       return Municipio.find.where().orderBy("nome").findList();
    }
}
