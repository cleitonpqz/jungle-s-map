package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.autor_modelo.*;
import javax.persistence.PersistenceException;
import play.libs.Json;
import models.*;

public class AutoresModelos extends Controller {
    
    public static Result GO_HOME = redirect(routes.AutoresModelos.manter(0, "nome", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
            return ok(
            manter.render(
                AutorModelo.page(page, 10, sortBy, order, filter),sortBy, order, filter)
            );
    }
    
    public static Result novoEditar(Long id, int quemChama) {
        Form<AutorModelo> autorForm;
        if(id==0){
                autorForm = form(AutorModelo.class);
        }else{
                autorForm = form(AutorModelo.class).fill(AutorModelo.find.byId(id));
        }
        
        return ok(
            novoEditar.render(id, autorForm, quemChama)
        );
    }

    public static Result salvar(Long id, int quemChama) {
        Form<AutorModelo> autorForm = form(AutorModelo.class).bindFromRequest();
        if(autorForm.hasErrors()) {
            return badRequest(novoEditar.render(id, autorForm, quemChama));
        }
        if(quemChama==2 && id!=0){
            flash("success", "Autor " + autorForm.get().nome + " foi editado com sucesso");
            autorForm.get().update(id);
        }else if(quemChama==0){
            flash("success", "Autor " + autorForm.get().nome + " foi incluido com sucesso");
            autorForm.get().save();
        }else{
            autorForm.get().save();
        }
        
        return ok(Json.toJson(autorForm.get()));
    }
    
    public static Result deletar(Long id) {
        try{
        AutorModelo.find.ref(id).delete();
        flash("success", "Autor excluido");
        return GO_HOME;
    }catch(PersistenceException exception){
        flash("error", "Exclusão não permitida. Existe Trabalho Científico vinculado a este autor");
        return GO_HOME;
    }
    }
     
}