package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.local.*;
import javax.persistence.PersistenceException;
import play.libs.Json;
import models.*;

public class Locais extends Controller{
    
    public static Result GO_HOME = redirect(routes.Locais.manter(0,"descricao", "asc", -1, -1, "", -1,-1));
    
       
    public static Result incluir() {
         Form<Local> localForm = form(Local.class);
         Form<TrabalhoCientifico> trabalhoCientificoForm = form(TrabalhoCientifico.class);
         return ok(incluir.render(localForm, trabalhoCientificoForm));
    }
     
     public static Result listarFormacao(Long id){ 
        return ok(Json.toJson(Formacao.opcoesPorBioma(id)));
    }
     
     public static Result opcoesFormacao(){ 
        return ok(Json.toJson(Formacao.opcoes()));
    }
    
    public static Result  salvar() {
        Form<Local> localForm = form(Local.class).bindFromRequest();
        Form<TrabalhoCientifico> trabalhoCientificoForm = form(TrabalhoCientifico.class);
        if(localForm.hasErrors()) {
           return badRequest(incluir.render(localForm, trabalhoCientificoForm));
        }
        localForm.get().save();
        flash("success", "O Local " + localForm.get().descricao + " foi incluido com sucesso");
        return GO_HOME;
       }
    
    public static Result localizarSimilar(int page, String sortBy, String order, int bioma, int formacao, String local, int estado, int cidade) {
        return ok(
            popUpLocalizarSimilar.render(
                Local.localizarSimilar(page, 5, sortBy, order, bioma, formacao,local, estado, cidade) ,
                sortBy, order, bioma, formacao, local, estado, cidade)
            );
    }
    
    public static Result manter(int page, String sortBy, String order, int bioma, int formacao, String local, int estado, int cidade) {
        return ok(
            manter.render(
                Local.localizarSimilar(page, 5, sortBy, order, bioma, formacao,local, estado, cidade) ,
                sortBy, order, bioma, formacao, local, estado, cidade)
            );
    }
    
    public static Result findById(Long id) {
        System.out.println(Local.find.byId(id).descricao);
        return ok(Json.toJson(Local.find.byId(id)));
    }
    
    public static Result excluir (Long id) {
        try{Local.find.ref(id).delete();
            flash("success", "Local excluido");
            return GO_HOME;
            }catch(PersistenceException exception){
             flash("error", "Local não permitida");
             return GO_HOME;
            }
    }
    
}
