package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.disponibilidade.*;
import javax.persistence.PersistenceException;
import models.*;

public class Disponibilidades extends Controller {
    
    public static Result GO_HOME = redirect(routes.Disponibilidades.manter(0, "descricao", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
        Form<Disponibilidade> disponibilidadeForm = form(Disponibilidade.class);
        return ok(
            manter.render(
                Disponibilidade.page(page, 10, sortBy, order, filter),sortBy, order, filter, disponibilidadeForm)
            );
    }
    
    public static Result editar(Long id) {
        Form<Disponibilidade> disponibilidadeForm = form(Disponibilidade.class).fill(
                Disponibilidade.find.byId(id)
        );
        return ok(
            editarForm.render(id, disponibilidadeForm)
        );
    }
    
     public static Result update(Long id) {
        Form<Disponibilidade> disponibilidadeForm = form(Disponibilidade.class).bindFromRequest();
        if(disponibilidadeForm.hasErrors()) {
            return badRequest(editarForm.render(id, disponibilidadeForm));
        }
        disponibilidadeForm.get().update(id);
        flash("success", "A Disponibilidade " + disponibilidadeForm.get().descricao + " foi alterada com sucesso");
        return GO_HOME;
    }
    
    public static Result salvar() {
        Form<Disponibilidade> disponibilidadeForm = form(Disponibilidade.class).bindFromRequest();
        if(disponibilidadeForm.hasErrors()) {
            return badRequest(manter.render(Disponibilidade.page(0, 10, "descricao", "asc", ""), "descricao", "asc", "", disponibilidadeForm));
        }
        disponibilidadeForm.get().save();
        flash("success", "A Disponibilidade " + disponibilidadeForm.get().descricao + " foi incluida com sucesso");
        return GO_HOME;
    }
    
    
    public static Result deletar(Long id) {
        try{
            Disponibilidade.find.ref(id).delete();
            flash("success", "Disponibilidade excluida com sucesso");
            return GO_HOME;
        }catch(PersistenceException exception){
            flash("error", "Exclusão não permitida. Há trabalho científico vinculado a esta disponibilidade");
        return GO_HOME;
        }   
    }
}