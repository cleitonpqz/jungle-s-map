package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.bioma.*;
import javax.persistence.PersistenceException;

import models.*;

public class Biomas extends Controller {
    
    public static Result GO_HOME = redirect(routes.Biomas.manter(0, "nome", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
        Form<Bioma> biomaForm = form(Bioma.class);
        return ok(
            manter.render(
                Bioma.page(page, 10, sortBy, order, filter),sortBy, order, filter, biomaForm)
            );
    }
    
    public static Result editar(Long id) {
        Form<Bioma> biomaForm = form(Bioma.class).fill(
                Bioma.find.byId(id)
        );
        return ok(
            editarForm.render(id, biomaForm)
        );
    }
    
     public static Result update(Long id) {
        Form<Bioma> biomaForm = form(Bioma.class).bindFromRequest();
        if(biomaForm.hasErrors()) {
            return badRequest(editarForm.render(id, biomaForm));
        }
        biomaForm.get().update(id);
        flash("success", "O Bioma " + biomaForm.get().nome + " foi alterado com sucesso");
        return GO_HOME;
    }
    
    public static Result salvar() {
        Form<Bioma> biomaForm = form(Bioma.class).bindFromRequest();
        if(biomaForm.hasErrors()) {
            return badRequest(manter.render(Bioma.page(0, 10, "nome", "asc", ""), "nome", "asc", "", biomaForm));
        }
        biomaForm.get().save();
        flash("success", "O Bioma " + biomaForm.get().nome + " foi incluido com sucesso");
        return GO_HOME;
    }
    
    
    public static Result deletar(Long id) {
        try{
        Bioma.find.ref(id).delete();
        flash("success", "Bioma excluido");
        return GO_HOME;
    }catch(PersistenceException exception){
        flash("error", "Exclusão não permitida. Existe Formações vinculadas a este bioma");
        return GO_HOME;
    }
    }
}