package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.variavel.*;
import javax.persistence.PersistenceException;
import play.libs.Json;

import models.*;

public class Variaveis extends Controller {
    
    public static Result GO_HOME = redirect(routes.Variaveis.manter(0, "nome", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
            return ok(
            manter.render(
                Variavel.page(page, 10, sortBy, order, filter),sortBy, order, filter)
            );
    }
    
    public static Result novaEditar(Long id, int quemChama) {
        Form<Variavel> variavelForm;
        if(id==0){
                variavelForm = form(Variavel.class);
        }else{
                variavelForm = form(Variavel.class).fill(Variavel.find.byId(id));
        }
        
        return ok(
            novoEditar.render(id, variavelForm, quemChama)
        );
    }

    public static Result salvar(Long id, int quemChama) {
        Form<Variavel> variavelForm = form(Variavel.class).bindFromRequest();
        if(variavelForm.hasErrors()) {
            return badRequest(novoEditar.render(id, variavelForm, quemChama));
        }
        if(quemChama==2 && id!=0){
            flash("success", "Variável " + variavelForm.get().nome + " foi editada com sucesso");
            variavelForm.get().update(id);
        }else if(quemChama==0){
            flash("success", "Variável " + variavelForm.get().nome + " foi incluida com sucesso");
            variavelForm.get().save();
        }else{
            variavelForm.get().save();
        }
        
        return ok(Json.toJson(variavelForm.get()));
    }
    
    public static Result deletar(Long id) {
        try{
            Variavel.find.ref(id).delete();
            flash("success", "Variavel excluída com sucesso");
            return GO_HOME;
        }catch(PersistenceException exception){
            flash("error", "Exclusão não permitida. Existem equações vinculadas a variavel");
        return GO_HOME;
        }   
    }
    
}