package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.espacamento.*;
import play.libs.Json;
import javax.persistence.PersistenceException;

import models.*;

public class Espacamentos extends Controller {
    
    public static Result GO_HOME = redirect(routes.Espacamentos.manter(0, "descricao", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
           return ok(
            manter.render(
                Espacamento.page(page, 10, sortBy, order, filter),sortBy, order, filter)
            );
    }
    
    public static Result novoEditar(Long id, int quemChama) {
        Form<Espacamento> espacamentoForm;
        if(id==0){
                espacamentoForm = form(Espacamento.class);
        }else{
                espacamentoForm = form(Espacamento.class).fill(Espacamento.find.byId(id));
        }
        
        return ok(
            novoEditar.render(id, espacamentoForm, quemChama)
        );
    }

    public static Result salvar(Long id, int quemChama) {
        Form<Espacamento> espacamentoForm = form(Espacamento.class).bindFromRequest();
        if(espacamentoForm.hasErrors()) {
            return badRequest(novoEditar.render(id, espacamentoForm, quemChama));
        }
        if(quemChama==2 && id!=0){
            flash("success", "Espaçamento " + espacamentoForm.get().descricao + " foi editado com sucesso");
            espacamentoForm.get().update(id);
        }else if(quemChama==0){
            flash("success", "Espaçamento " + espacamentoForm.get().descricao + " foi incluido com sucesso");
            espacamentoForm.get().save();
        }else{
            espacamentoForm.get().save();
        }
        
        return ok(Json.toJson(espacamentoForm.get()));
    }
    
     public static Result deletar(Long id) {
        try{
            Espacamento.find.ref(id).delete();
            flash("success", "Espaçamento excluida com sucesso");
            return GO_HOME;
        }catch(PersistenceException exception){
            flash("error", "Exclusão não permitida. Existem locais vinculados a este espaçamento");
        return GO_HOME;
        }   
    }

}