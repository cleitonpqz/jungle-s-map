package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.metodoQB.*;
import javax.persistence.PersistenceException;

import models.*;

public class MetodosQB extends Controller {
    
    public static Result GO_HOME = redirect(routes.MetodosQB.manter());
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter() {
        Form<MetodoQuantificacaoBiomassa> metodoQBForm = form(MetodoQuantificacaoBiomassa.class);
        return ok(
            manter.render(
                MetodoQuantificacaoBiomassa.list("descricao","asc", ""), metodoQBForm)
            );
    }
    
    public static Result editar(Long id) {
        Form<MetodoQuantificacaoBiomassa> metodoQBForm = form(MetodoQuantificacaoBiomassa.class).fill(
                MetodoQuantificacaoBiomassa.find.byId(id)
        );
        return ok(
            editarForm.render(id, metodoQBForm)
        );
    }
    
     public static Result update(Long id) {
        Form<MetodoQuantificacaoBiomassa> metodoQBForm = form(MetodoQuantificacaoBiomassa.class).bindFromRequest();
        if(metodoQBForm.hasErrors()) {
            return badRequest(editarForm.render(id, metodoQBForm));
        }
        metodoQBForm.get().update(id);
        flash("success", "O Método de Quantificação de Biomassa " + metodoQBForm.get().descricao + " foi alterado com sucesso");
        return GO_HOME;
    }
    
       
    public static Result salvar() {
        Form<MetodoQuantificacaoBiomassa> metodoQBForm = form(MetodoQuantificacaoBiomassa.class).bindFromRequest();
        if(metodoQBForm.hasErrors()) {
            return badRequest(manter.render(MetodoQuantificacaoBiomassa.list("descricao", "asc", ""), metodoQBForm));
        }
        metodoQBForm.get().save();
        flash("success", "O Método de Quantificação de Biomassa " + metodoQBForm.get().descricao + " foi incluido com sucesso");
        return GO_HOME;
    }
    
    
    public static Result deletar(Long id) {
        try{
        MetodoQuantificacaoBiomassa.find.ref(id).delete();
        flash("success", "Metodo de Quantificação de Biomassa excluido");
        return GO_HOME;
    }catch(PersistenceException exception){
        flash("error", "Exclusão não permitida. Existe Trabalho Científico vinculadas a este método");
        return GO_HOME;
    }
    }
}