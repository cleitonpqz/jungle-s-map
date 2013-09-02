package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.metodoQC.*;
import javax.persistence.PersistenceException;

import models.*;

public class MetodosQC extends Controller {
    
    public static Result GO_HOME = redirect(routes.MetodosQC.manter());
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter() {
        Form<MetodoQuantificacaoCarbono> metodoQCForm = form(MetodoQuantificacaoCarbono.class);
        return ok(
            manter.render(
                MetodoQuantificacaoCarbono.list("descricao","asc", ""), metodoQCForm)
            );
    }
    
    public static Result editar(Long id) {
        Form<MetodoQuantificacaoCarbono> metodoQCForm = form(MetodoQuantificacaoCarbono.class).fill(
                MetodoQuantificacaoCarbono.find.byId(id)
        );
        return ok(
            editarForm.render(id, metodoQCForm)
        );
    }
    
     public static Result update(Long id) {
        Form<MetodoQuantificacaoCarbono> metodoQCForm = form(MetodoQuantificacaoCarbono.class).bindFromRequest();
        if(metodoQCForm.hasErrors()) {
            return badRequest(editarForm.render(id, metodoQCForm));
        }
        metodoQCForm.get().update(id);
        flash("success", "O Método de Quantificação de Carbono " + metodoQCForm.get().descricao + " foi alterado com sucesso");
        return GO_HOME;
    }
    
       
    public static Result salvar() {
        Form<MetodoQuantificacaoCarbono> metodoQCForm = form(MetodoQuantificacaoCarbono.class).bindFromRequest();
        if(metodoQCForm.hasErrors()) {
            return badRequest(manter.render(MetodoQuantificacaoCarbono.list("descricao", "asc", ""), metodoQCForm));
        }
        metodoQCForm.get().save();
        flash("success", "O Método de Quantificação de Carbono " + metodoQCForm.get().descricao + " foi incluido com sucesso");
        return GO_HOME;
    }
    
    
    public static Result deletar(Long id) {
        try{
        MetodoQuantificacaoCarbono.find.ref(id).delete();
        flash("success", "Metodo de Quantificação de Carbono excluido");
        return GO_HOME;
    }catch(PersistenceException exception){
        flash("error", "Exclusão não permitida. Existe Trabalho Científico vinculadas a este método");
        return GO_HOME;
    }
    }
}