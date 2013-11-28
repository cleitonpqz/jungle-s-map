package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.disponibilidade.*;
import javax.persistence.PersistenceException;
import play.libs.Json;
import models.*;

public class Disponibilidades extends Controller {
    
    public static Result GO_HOME = redirect(routes.Disponibilidades.manter(0, "descricao", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
            return ok(
            manter.render(
                Disponibilidade.page(page, 10, sortBy, order, filter),sortBy, order, filter)
            );
    }
    
    public static Result novaEditar(Long id, int quemChama) {
        Form<Disponibilidade> disponibilidadeForm;
        if(id==0){
                disponibilidadeForm = form(Disponibilidade.class);
        }else{
                disponibilidadeForm = form(Disponibilidade.class).fill(Disponibilidade.find.byId(id));
        }
        
        return ok(
            novoEditar.render(id, disponibilidadeForm, quemChama)
        );
    }

    public static Result salvar(Long id, int quemChama) {
        Form<Disponibilidade> disponibilidadeForm = form(Disponibilidade.class).bindFromRequest();
        if(disponibilidadeForm.hasErrors()) {
            return badRequest(novoEditar.render(id, disponibilidadeForm, quemChama));
        }
        if(quemChama==2 && id!=0){
            flash("success", "Disponibilidade " + disponibilidadeForm.get().descricao + " foi editada com sucesso");
            disponibilidadeForm.get().update(id);
        }else if(quemChama==0){
            flash("success", "Disponibilidade " + disponibilidadeForm.get().descricao + " foi incluida com sucesso");
            disponibilidadeForm.get().save();
        }else{
            disponibilidadeForm.get().save();
        }
        
        return ok(Json.toJson(disponibilidadeForm.get()));
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