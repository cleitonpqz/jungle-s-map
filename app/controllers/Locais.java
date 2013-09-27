package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.local.*;
import javax.persistence.PersistenceException;
import play.libs.Json;
import org.codehaus.jackson.JsonNode;
import models.*;

public class Locais extends Controller{
    
    public static Result GO_HOME = redirect(routes.Application.index());
    
    public static Result index() {
        return GO_HOME;
    }
    
     public static Result incluir() {
         Form<Local> localForm = form(Local.class);
         Form<TrabalhoCientifico> trabalhoCientificoForm = form(TrabalhoCientifico.class);
         return ok(incluir.render(localForm, trabalhoCientificoForm));
    }
     
     public static Result listarFormacao(Long id){ 
         Formacao.opcoesPorBioma(id);
         return ok(Json.toJson(Formacao.opcoesPorBioma(id)));
    }
    
    public static Result  salvar() {
        Form<Local> localForm = form(Local.class).bindFromRequest();
        Form<TrabalhoCientifico> trabalhoCientificoForm = form(TrabalhoCientifico.class);
        if(localForm.hasErrors()) {
           return badRequest(incluir.render(localForm, trabalhoCientificoForm));
        }
        localForm.get().save();
        flash("success", "O Local " + localForm.get().descricao + " foi incluido com sucesso");
        return GO_HOME;
       }
    
    public static Result salvarModal() {
         String autor_id = form().bindFromRequest().get("autor.id");
         String disponibilidade_id = form().bindFromRequest().get("disponibilidade.id");
         String metodoQB_id = form().bindFromRequest().get("metodo_quantificacao_biomassa.id");
         String metodoQC_id = form().bindFromRequest().get("metodo_quantificacao_carbono.id");
         TrabalhoCientifico trabalhoCientifico = new TrabalhoCientifico();
         trabalhoCientifico.autor = Autor.find.byId(Long.parseLong(autor_id));
         trabalhoCientifico.disponibilidade = Disponibilidade.find.byId(Long.parseLong(disponibilidade_id));
         trabalhoCientifico.metodo_quantificacao_biomassa = MetodoQuantificacaoBiomassa.find.byId(Long.parseLong(metodoQB_id));
         trabalhoCientifico.metodo_quantificacao_carbono = MetodoQuantificacaoCarbono.find.byId(Long.parseLong(metodoQC_id));
         trabalhoCientifico.save();
         System.out.println(metodoQB_id);
         return ok(Json.toJson(trabalhoCientifico));
    }
}
