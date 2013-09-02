package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.espacamento.*;
import javax.persistence.PersistenceException;

import models.*;

public class Espacamentos extends Controller {
    
    public static Result GO_HOME = redirect(routes.Espacamentos.manter(0, "descricao", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
        Form<Espacamento> biomaForm = form(Espacamento.class);
        return ok(
            manter.render(
                Espacamento.page(page, 10, sortBy, order, filter),sortBy, order, filter, biomaForm)
            );
    }
    
    public static Result editar(Long id) {
        Form<Espacamento> espacamentoForm = form(Espacamento.class).fill(
                Espacamento.find.byId(id)
        );
        return ok(
            editarForm.render(id, espacamentoForm)
        );
    }
    
     public static Result update(Long id) {
        Form<Espacamento> espacamentoForm = form(Espacamento.class).bindFromRequest();
        if(espacamentoForm.hasErrors()) {
            return badRequest(editarForm.render(id, espacamentoForm));
        }
        espacamentoForm.get().update(id);
        flash("success", "O Espaçamento " + espacamentoForm.get().descricao + " foi alterado com sucesso");
        return GO_HOME;
    }
    
    public static Result salvar() {
        Form<Espacamento> espacamentoForm = form(Espacamento.class).bindFromRequest();
        if(espacamentoForm.hasErrors()) {
            return badRequest(manter.render(Espacamento.page(0, 10, "descrição", "asc", ""), "descricao", "asc", "", espacamentoForm));
        }
        espacamentoForm.get().save();
        flash("success", "O Espaçamento " + espacamentoForm.get().descricao + " foi incluido com sucesso");
        return GO_HOME;
    }
    
    
    public static Result deletar(Long id) {
        try{
        Espacamento.find.ref(id).delete();
        flash("success", "Espacamento excluido");
        return GO_HOME;
    }catch(PersistenceException exception){
        flash("error", "Exclusão não permitida. Existem locais vinculadas a este espaçamento");
        return GO_HOME;
    }
    }
}