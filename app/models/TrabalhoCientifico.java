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
    public Disponibilidade disponibilidade;

    @ManyToOne
    @Constraints.Required(message="O campo Método de Quantificação de Biomassa é obrigatório!")
    public MetodoQuantificacaoBiomassa metodo_quantificacao_biomassa;

    @ManyToOne
    @Constraints.Required(message="O campo Método de Quantificação de Carbono é obrigatório!")
    public MetodoQuantificacaoCarbono metodo_quantificacao_carbono;
    
    @Constraints.Required(message="O campo autor é obrigatório!")
    public Integer ano;
    
    @OneToMany(targetEntity = TrabalhoCientificoEquacao.class, cascade = CascadeType.ALL)
    public List<TrabalhoCientificoEquacao> trabalho_cientifico_equacao = new ArrayList<TrabalhoCientificoEquacao>();
    
    public static Model.Finder<Long,TrabalhoCientifico> find = new Model.Finder<Long,TrabalhoCientifico>(Long.class, TrabalhoCientifico.class);

    public static Map<String,String> opcoes() {
        LinkedHashMap<String,String> opcoes = new LinkedHashMap<String,String>();
        for(TrabalhoCientifico t: TrabalhoCientifico.find.orderBy("autor.nome").findList()) {
            opcoes.put(t.id.toString(), t.autor.nome +" ("+t.ano+")");
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
