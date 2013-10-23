package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.variavel.*;
import javax.persistence.PersistenceException;

import models.*;

public class Variaveis extends Controller {
    
    public static Result GO_HOME = redirect(routes.Variaveis.manter(0, "nome", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
        Form<Variavel> variavelForm = form(Variavel.class);
        return ok(
            manter.render(
                Variavel.page(page, 10, sortBy, order, filter),sortBy, order, filter, variavelForm)
            );
    }
    
    public static Result editar(Long id) {
        Form<Variavel> variavelForm = form(Variavel.class).fill(
                Variavel.find.byId(id)
        );
        return ok(
            editarForm.render(id, variavelForm)
        );
    }
    
     public static Result update(Long id) {
        Form<Variavel> variavelForm = form(Variavel.class).bindFromRequest();
        if(variavelForm.hasErrors()) {
            return badRequest(editarForm.render(id, variavelForm));
        }
        variavelForm.get().update(id);
        flash("success", "O Variável " + variavelForm.get().nome + " foi alterado com sucesso");
        return GO_HOME;
    }
    
    public static Result salvar() {
        Form<Variavel> variavelForm = form(Variavel.class).bindFromRequest();
        if(variavelForm.hasErrors()) {
            return badRequest(manter.render(Variavel.page(0, 10, "nome", "asc", ""), "nome", "asc", "", variavelForm));
        }
        variavelForm.get().save();
        flash("success", "A Variável " + variavelForm.get().nome + " foi incluida com sucesso");
        return GO_HOME;
    }
    
    
    public static Result deletar(Long id) {
        Variavel.find.ref(id).delete();
        flash("success", "Variável excluido");
        return GO_HOME;
    
    }
}