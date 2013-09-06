package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.trabalho_cientifico.*;
import javax.persistence.PersistenceException;
import models.*;

public class TrabalhosCientificos extends Controller {
    
    public static Result GO_HOME = redirect(routes.TrabalhosCientificos.manter(0, "autor.nome", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
        Form<TrabalhoCientifico> trabalhoCientificoForm = form(TrabalhoCientifico.class);
        return ok(
            manter.render(
                TrabalhoCientifico.page(page, 10, sortBy, order, filter),sortBy, order, filter, trabalhoCientificoForm)
            );
    }
    
    public static Result editar(Long id) {
        Form<TrabalhoCientifico> trabalhoCientificoForm = form(TrabalhoCientifico.class).fill(
                TrabalhoCientifico.find.byId(id)
        );
        return ok(
            editarForm.render(id, trabalhoCientificoForm)
        );
    }
    
     public static Result update(Long id) {
        Form<TrabalhoCientifico> trabalhoCientificoForm = form(TrabalhoCientifico.class).bindFromRequest();
        if(trabalhoCientificoForm.hasErrors()) {
            return badRequest(editarForm.render(id, trabalhoCientificoForm));
        }
        trabalhoCientificoForm.get().update(id);
        flash("success", "O Trabalho Científico do autor " + trabalhoCientificoForm.get().autor.nome + " foi alterado com sucesso");
        return GO_HOME;
    }
    
    public static Result salvar() {
        Form<TrabalhoCientifico> trabalhoCientificoForm = form(TrabalhoCientifico.class).bindFromRequest();
        if(trabalhoCientificoForm.hasErrors()) {
            return badRequest(manter.render(TrabalhoCientifico.page(0, 10, "nome", "asc", ""), "nome", "asc", "", trabalhoCientificoForm));
        }
        trabalhoCientificoForm.get().save();
        flash("success", "O Trabalho Científico do autor " + trabalhoCientificoForm.get().autor.nome + " foi incluído com sucesso");
        return GO_HOME;
    }
    
    
    public static Result deletar(Long id) {
        try{
            TrabalhoCientifico.find.ref(id).delete();
            flash("success", "Trabalho Científico excluído com sucesso");
            return GO_HOME;
        }catch(PersistenceException exception){
            flash("error", "Exclusão não permitida. Existe registros vinculados a este Trabalho Científico");
        return GO_HOME;
        }   
    }
}