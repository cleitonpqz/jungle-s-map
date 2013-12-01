package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.metodoQB.*;
import play.libs.Json;
import javax.persistence.PersistenceException;

import models.*;

public class MetodosQB extends Controller {
    
    public static Result GO_HOME = redirect(routes.MetodosQB.manter());
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter() {
          return ok(
            manter.render(
                MetodoQuantificacaoBiomassa.list("descricao","asc", ""))
            );
    }
    
    public static Result novoEditar(Long id, int quemChama) {
        Form<MetodoQuantificacaoBiomassa> metodoQBForm;
        if(id==0){
                metodoQBForm = form(MetodoQuantificacaoBiomassa.class);
        }else{
                metodoQBForm = form(MetodoQuantificacaoBiomassa.class).fill(MetodoQuantificacaoBiomassa.find.byId(id));
        }
        
        return ok(
                novoEditar.render(id, metodoQBForm, quemChama)
        );
    }

    public static Result salvar(Long id, int quemChama) {
        Form<MetodoQuantificacaoBiomassa> metodoQBForm = form(MetodoQuantificacaoBiomassa.class).bindFromRequest();
        if(metodoQBForm.hasErrors()) {
            return badRequest(novoEditar.render(id, metodoQBForm, quemChama));
        }
        if(quemChama==2 && id!=0){
            flash("success", "MetodoQB " + metodoQBForm.get().descricao + " foi editado com sucesso");
            metodoQBForm.get().update(id);
        }else if(quemChama==0){
            flash("success", "MetodoQB " + metodoQBForm.get().descricao + " foi incluido com sucesso");
            metodoQBForm.get().save();
        }else{
            metodoQBForm.get().save();
        }
        
        return ok(Json.toJson(metodoQBForm.get()));
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