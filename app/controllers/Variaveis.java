package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.variavel.*;
import javax.persistence.PersistenceException;
import play.libs.Json;
import play.libs.Json.*;
import com.avaje.ebean.*;
import static play.libs.Json.toJson;

import models.*;

public class Variaveis extends Controller {
    
    public static Result GO_HOME = redirect(routes.Variaveis.manter(0, "nome", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
        Form<Variavel> variavelForm = form(Variavel.class);
        return ok(
            manter.render(
                Variavel.page(page, 10, sortBy, order, filter),sortBy, order, filter, variavelForm)
            );
    }
    
    public static Result editar(Long id) {
        Form<Variavel> variavelForm = form(Variavel.class).fill(
                Variavel.find.byId(id)
        );
        return ok(
            editarForm.render(id, variavelForm)
        );
    }
    
     public static Result update(Long id) {
        Form<Variavel> variavelForm = form(Variavel.class).bindFromRequest();
        if(variavelForm.hasErrors()) {
            return badRequest(editarForm.render(id, variavelForm));
        }
        variavelForm.get().update(id);
        flash("success", "O Variável " + variavelForm.get().nome + " foi alterado com sucesso");
        return GO_HOME;
    }
    
    public static Result salvar() {
        Form<Variavel> variavelForm = form(Variavel.class).bindFromRequest();
        if(variavelForm.hasErrors()) {
            return badRequest(manter.render(Variavel.page(0, 10, "nome", "asc", ""), "nome", "asc", "", variavelForm));
        }
        variavelForm.get().save();
        flash("success", "A Variável " + variavelForm.get().nome + " foi incluida com sucesso");
        return GO_HOME;
    }
    
    public static Result novo() {
        Form<Variavel> variavelForm = form(Variavel.class);
        return ok(
        novo.render(variavelForm)
        );
    }
    public static Result salvarSelecionar() {
        Form<Variavel> variavelForm = form(Variavel.class).bindFromRequest();
        if(variavelForm.hasErrors()) {
            return badRequest(novo.render(variavelForm));
        }
        variavelForm.get().save();
        return ok(Json.toJson(variavelForm.get()));
    }
  
    public static Result deletar(Long id) {
        Variavel.find.ref(id).delete();
        flash("success", "Variável excluida");
        return GO_HOME;
    
    }

    public static Result findByLocal(Long id) {
         
        String sql =" SELECT " 
                      +"variavel.id," 
                      +"variavel.sigla," 
                      +"variavel.nome "
                    +" FROM "
                      +"public.variavel," 
                      +"public.local, "
                      +"public.trabalho_cientifico, "
                      +"public.trabalho_cientifico_equacao," 
                      +"public.trabalho_cientifico_modelo," 
                      +"public.modelo," 
                      +"public.equacao," 
                      +"public.modelo_variavel," 
                      +"public.equacao_variavel"
                    +" WHERE "
                      +"local.trabalho_cientifico_id = trabalho_cientifico.id AND " 
                      +"trabalho_cientifico.id = trabalho_cientifico_equacao.trabalho_cientifico_id AND "
                      +"trabalho_cientifico.id = trabalho_cientifico_modelo.trabalho_cientifico_id AND "
                      +"trabalho_cientifico_equacao.equacao_id = equacao.id AND "
                      +"trabalho_cientifico_modelo.modelo_id = modelo.id AND "
                      +"modelo.id = modelo_variavel.modelo_id AND "
                      +"equacao.id = equacao_variavel.equacao_id AND "
                      +"modelo_variavel.variavel_id = variavel.id AND "
                      +"equacao_variavel.variavel_id = variavel.id AND "
                      +"local.id = '" + id + "'";
         SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
        return ok(Json.toJson(sqlQuery.findList()));
}
}