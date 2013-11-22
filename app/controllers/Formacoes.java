package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.formacao.*;
import javax.persistence.PersistenceException;
import play.libs.Json;
import models.*;

public class Formacoes extends Controller {
    
    public static Result GO_HOME = redirect(routes.Formacoes.manter(0, "nome", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
        Form<Formacao> formacaoForm = form(Formacao.class);
        return ok(
            manter.render(
                Formacao.page(page, 10, sortBy, order, filter),sortBy, order, filter, formacaoForm)
            );
    }
    
    public static Result editar(Long id) {
        Form<Formacao> formacaoForm = form(Formacao.class).fill(
                Formacao.find.byId(id)
        );
        return ok(
            editarForm.render(id, formacaoForm)
        );
    }
    
     public static Result update(Long id) {
        Form<Formacao> formacaoForm = form(Formacao.class).bindFromRequest();
        if(formacaoForm.hasErrors()) {
            return badRequest(editarForm.render(id, formacaoForm));
        }
        formacaoForm.get().update(id);
        flash("success", "A Formação " + formacaoForm.get().nome + " foi alterada com sucesso");
        return GO_HOME;
    }
    
    public static Result salvar() {
        Form<Formacao> formacaoForm = form(Formacao.class).bindFromRequest();
        if(form().bindFromRequest().get("bioma.id")==null 
            || form().bindFromRequest().get("bioma.id").equals("")) {
            formacaoForm.reject("bioma.id", "O campo Bioma é de preenchimento obrigatório!");
        }
        if(formacaoForm.hasErrors()) {
            return badRequest(manter.render(Formacao.page(0, 10, "nome", "asc", ""), "nome", "asc", "", formacaoForm));
        }
        formacaoForm.get().save();
        flash("success", "A Formação " + formacaoForm.get().nome + " foi incluida com sucesso");
        return GO_HOME;
    }
    
    
    public static Result deletar(Long id) {
        try{
            Formacao.find.ref(id).delete();
            flash("success", "Formação excluida com sucesso");
            return GO_HOME;
        }catch(PersistenceException exception){
            flash("error", "Exclusão não permitida. Existem locais vinculados a esta formação");
        return GO_HOME;
        }   
    }
    
    public static Result listarFormacao(Long id){ 
       return ok(Json.toJson(Formacao.opcoesPorBioma(id)));
    }
     
 }