package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.metodoQC.*;
import play.libs.Json;
import javax.persistence.PersistenceException;

import models.*;

public class MetodosQC extends Controller {
    
    public static Result GO_HOME = redirect(routes.MetodosQC.manter());
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter() {
          return ok(
            manter.render(
                MetodoQuantificacaoCarbono.list("descricao","asc", ""))
            );
    }
    
   public static Result novoEditar(Long id, int quemChama) {
        Form<MetodoQuantificacaoCarbono> metodoQCForm;
        if(id==0){
                metodoQCForm = form(MetodoQuantificacaoCarbono.class);
        }else{
                metodoQCForm = form(MetodoQuantificacaoCarbono.class).fill(MetodoQuantificacaoCarbono.find.byId(id));
        }
        
        return ok(
                novoEditar.render(id, metodoQCForm, quemChama)
        );
    }

    public static Result salvar(Long id, int quemChama) {
        Form<MetodoQuantificacaoCarbono> metodoQCForm = form(MetodoQuantificacaoCarbono.class).bindFromRequest();
        if(metodoQCForm.hasErrors()) {
            return badRequest(novoEditar.render(id, metodoQCForm, quemChama));
        }
        if(quemChama==2 && id!=0){
            flash("success", "MetodoQC " + metodoQCForm.get().descricao + " foi editado com sucesso");
            metodoQCForm.get().update(id);
        }else if(quemChama==0){
            flash("success", "MetodoQC " + metodoQCForm.get().descricao + " foi incluido com sucesso");
            metodoQCForm.get().save();
        }else{
            metodoQCForm.get().save();
        }
        
        return ok(Json.toJson(metodoQCForm.get()));
    }
    
    
    public static Result deletar(Long id) {
        try{
        MetodoQuantificacaoCarbono.find.ref(id).delete();
        flash("success", "Metodo de Quantificação de Carbono excluído");
        return GO_HOME;
    }catch(PersistenceException exception){
        flash("error", "Exclusão não permitida. Existe Trabalho Científico vinculadas a este método");
        return GO_HOME;
    }
    }
}