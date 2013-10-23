package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.autor_modelo.*;
import javax.persistence.PersistenceException;

import models.*;

public class AutoresModelos extends Controller {
    
    public static Result GO_HOME = redirect(routes.AutoresModelos.manter(0, "nome", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
        Form<AutorModelo> autorForm = form(AutorModelo.class);
        return ok(
            manter.render(
                AutorModelo.page(page, 10, sortBy, order, filter),sortBy, order, filter, autorForm)
            );
    }
    
    public static Result editar(Long id) {
        Form<AutorModelo> autorForm = form(AutorModelo.class).fill(
                AutorModelo.find.byId(id)
        );
        return ok(
            editarForm.render(id, autorForm)
        );
    }
    
     public static Result update(Long id) {
        Form<AutorModelo> autorForm = form(AutorModelo.class).bindFromRequest();
        if(autorForm.hasErrors()) {
            return badRequest(editarForm.render(id, autorForm));
        }
        autorForm.get().update(id);
        flash("success", "O Autor " + autorForm.get().nome + " foi alterado com sucesso");
        return GO_HOME;
    }
    
        
    public static Result salvar() {
        Form<AutorModelo> autorForm = form(AutorModelo.class).bindFromRequest();
        if(autorForm.hasErrors()) {
            return badRequest(manter.render(AutorModelo.page(0, 10, "nome", "asc", ""), "nome", "asc", "", autorForm));
        }
        autorForm.get().save();
        flash("success", "O Autor " + autorForm.get().nome + " foi incluido com sucesso");
        return GO_HOME;
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