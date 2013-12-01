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
    
    public static Result novaEditar(Long id, int quemChama) {
        Form<Formacao> formacaoForm;
        if(id==0){
                formacaoForm = form(Formacao.class);
        }else{
                formacaoForm = form(Formacao.class).fill(Formacao.find.byId(id));
        }
        
        return ok(
            novoEditar.render(id, formacaoForm, quemChama)
        );
    }

    public static Result salvar(Long id, int quemChama) {
        Form<Formacao> formacaoForm = form(Formacao.class).bindFromRequest();
        if(formacaoForm.hasErrors()) {
            return badRequest(novoEditar.render(id, formacaoForm, quemChama));
        }
        if(quemChama==2 && id!=0){
            flash("success", "Formação " + formacaoForm.get().nome + " foi editada com sucesso");
            formacaoForm.get().update(id);
        }else if(quemChama==0){
            flash("success", "Formação " + formacaoForm.get().nome + " foi incluida com sucesso");
            formacaoForm.get().save();
        }else{
            formacaoForm.get().save();
        }
        
        return ok(Json.toJson(formacaoForm.get()));
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
        return ok(
            manter.render(
                Formacao.page(page, 10, sortBy, order, filter),sortBy, order, filter)
            );
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