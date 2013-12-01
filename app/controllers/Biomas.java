package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.bioma.*;
import javax.persistence.PersistenceException;
import play.libs.Json;
import play.libs.Json.*;
import static play.libs.Json.toJson;

import models.*;

public class Biomas extends Controller {
    
    public static Result GO_HOME = redirect(routes.Biomas.manter(0, "nome", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }

    public static Result novoEditar(Long id, int quemChama, Long idFormacao) {
        Form<Bioma> biomaForm;
        if(id==0){
                biomaForm = form(Bioma.class);
        }else{
                biomaForm = form(Bioma.class).fill(Bioma.find.byId(id));
        }
        
        return ok(
            novoEditar.render(id, biomaForm, quemChama, idFormacao));
    }

    public static Result salvar(Long id, int quemChama, Long idFormacao) {
        Form<Bioma> biomaForm = form(Bioma.class).bindFromRequest();
        if(biomaForm.hasErrors()) {
            return badRequest(novoEditar.render(id, biomaForm, quemChama, idFormacao));
        }
        if(quemChama==2 && id!=0){
            flash("success", "O Bioma " + biomaForm.get().nome + " foi editado com sucesso");
            biomaForm.get().update(id);
        }else if(quemChama==0){
            flash("success", "O Bioma " + biomaForm.get().nome + " foi incluido com sucesso");
            biomaForm.get().save();
        }else{
            biomaForm.get().save();
        }
        
        return ok(Json.toJson(biomaForm.get()));
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
        return ok(
            manter.render(
                Bioma.page(page, 10, sortBy, order, filter),sortBy, order, filter)
            );
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