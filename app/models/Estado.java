package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class Estado extends Model {
    
    @Id
    @Column(name="ibge")
    public Long id;

    @Constraints.Required(message="O campo Sigla é obrigatório!")
    @Column(length=2)
    public String sigla;
    
    @Constraints.Required(message="O campo Estado é obrigatório!")
    public String nome;

    @Column(columnDefinition="numeric(15,5)")
    public Double area;
    
    public static Model.Finder<Long,Estado> find = new Model.Finder<Long,Estado>(Long.class, Estado.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(Estado b: Estado.find.orderBy("nome").findList()) {
            opcoes.put(b.id.toString(), b.nome);
         }
        return opcoes;
    }
    
    public static Page<Estado> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("nome", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
