package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.bioma.*;
import javax.persistence.PersistenceException;

import models.*;

public class Compartimentos extends Controller {
    
    public static Result GO_HOME = redirect(routes.Compartimentos.manter(0, "descricao", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
        Form<Compartimento> compartimentoForm = form(Compartimento.class);
        return ok(
            manter.render(
                Compartimento.page(page, 10, sortBy, order, filter),sortBy, order, filter, compartimentoForm)
            );
    }
    
    public static Result editar(Long id) {
        Form<Compartimento> compartimentoForm = form(Compartimento.class).fill(
                Compartimento.find.byId(id)
        );
        return ok(
            editarForm.render(id, compartimentoForm)
        );
    }
    
     public static Result update(Long id) {
        Form<Compartimento> compartimentoForm = form(Compartimento.class).bindFromRequest();
        if(comapartimentoForm.hasErrors()) {
            return badRequest(editarForm.render(id, compartimentoForm));
        }
        compartimentoForm.get().update(id);
        flash("success", "O Compartimento " + compartimentoForm.get().descricao + " foi alterado com sucesso");
        return GO_HOME;
    }
    
    public static Result salvar() {
        Form<Compartimento> compartimentoForm = form(Compartimento.class).bindFromRequest();
        if(compartimentoForm.hasErrors()) {
            return badRequest(manter.render(Compartimento.page(0, 10, "descricao", "asc", ""), "descricao", "asc", "", compartimentoForm));
        }
        compartimentoForm.get().save();
        flash("success", "O Compartimento " + compartimentoForm.get().descricao + " foi incluido com sucesso");
        return GO_HOME;
    }
    
    
    public static Result deletar(Long id) {
        try{
        Compartimento.find.ref(id).delete();
        flash("success", "Compartimento excluido");
        return GO_HOME;
    }catch(PersistenceException exception){
        flash("error", "Exclusão não permitida. Existe Formações vinculadas a este Compartimento");
        return GO_HOME;
    }
    }
}