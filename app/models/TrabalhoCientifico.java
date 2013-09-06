package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
public class TrabalhoCientifico extends Model {
    
    @Id
    public Long id;

    @ManyToOne
    @Constraints.Required(message="O campo autor é obrigatório!")
    @Formats.NonEmpty
    public Autor autor;

    @ManyToOne
    @Constraints.Required(message="O campo Disponibilidade é obrigatório!")
    @Formats.NonEmpty
    public Disponibilidade disponibilidade;

    @ManyToOne
    @Constraints.Required(message="O campo Método de Quantificação de Biomassa é obrigatório!")
    @Formats.NonEmpty
    public MetodoQuantificacaoBiomassa metodo_quantificacao_biomassa;

    @ManyToOne
    @Constraints.Required(message="O campo Método de Quantificação de Carbono é obrigatório!")
    @Formats.NonEmpty
    public MetodoQuantificacaoCarbono metodo_quantificacao_carbono;
    
    public static Model.Finder<Long,TrabalhoCientifico> find = new Model.Finder<Long,TrabalhoCientifico>(Long.class, TrabalhoCientifico.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(TrabalhoCientifico f: TrabalhoCientifico.find.orderBy("autor.nome").findList()) {
            opcoes.put(f.id.toString(), f.autor.nome);
        }
        return opcoes;
    }

    public static Page<TrabalhoCientifico> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("autor.nome", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
}
