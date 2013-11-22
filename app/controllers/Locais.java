package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.local.*;
import javax.persistence.PersistenceException;
import play.libs.Json.*;
import play.libs.Json;   
import static play.libs.Json.toJson;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;    
import models.*;

public class Locais extends Controller{
    
    public static Result GO_HOME = redirect(routes.Application.index());
    
       
    public static Result incluir() {
         Form<Local> localForm = form(Local.class);
         return ok(incluir.render(localForm));
    }
     
     public static Result listarFormacao(Long id){ 
        return ok(Json.toJson(Formacao.opcoesPorBioma(id)));
    }
     
     public static Result opcoesFormacao(){ 
        return ok(Json.toJson(Formacao.opcoes()));
    }
    
    public static Result  salvar() {
        Form<Local> localForm = form(Local.class).bindFromRequest();
        if(localForm.hasErrors()) {
           return badRequest(incluir.render(localForm));
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
    
   
    public static Result excluir (Long id) {
        Result VOLTAR = redirect(routes.Locais.manter(0,"descricao", "asc", -1, -1, "", -1,-1));
        try{Local.find.ref(id).delete();
            flash("success", "Local excluido");
            return VOLTAR;
            }catch(PersistenceException exception){
             flash("error", "Exclusão não permitida");
             return VOLTAR;
            }
    }
    public static Result editar(Long id) {
         Local local = Local.find.byId(id);
         Form<Local> localForm = form(Local.class).fill(local);
         return ok(
         editarForm.render(id, localForm, local.coordenadas)
         );
    }
        
    public static Result atualizar(Long id) {
        Result VOLTAR = redirect(routes.Locais.manter(0,"descricao", "asc", -1, -1, "", -1,-1));
        Form<Local> localForm = form(Local.class).bindFromRequest();
        if(localForm.hasErrors()) {
        return badRequest(editarForm.render(id, localForm, localForm.get().coordenadas));
        }
        localForm.get().update(id);
        flash("success", "O Local " + localForm.get().descricao + " foi alterado com sucesso");
        return VOLTAR;
    }
    
     public static Result findById(Long id) {
        ObjectNode result = Json.newObject();
        String coordenadas ="";
        String municipios="";
        Local local = Local.find.byId(id);
       	result.put("id", local.id);
	result.put("descricao", local.descricao);
        result.put("area_total", local.area_total);
	result.put("trabalho_cientifico", Json.toJson(local.trabalho_cientifico));
        result.put("formacao", Json.toJson(local.formacao));
	result.put("espacamento", Json.toJson(local.espacamento.descricao));
        result.put("area_total", local.area_total);
	result.put("trabalho_cientifico", Json.toJson(local.trabalho_cientifico));
        
        for(Coordenada coordenada: local.coordenadas){
             coordenadas = coordenadas+"("+coordenada.latitude+","+coordenada.longitude+")";
        }
        result.put("coordenadas", Json.toJson(coordenadas));
        
        for(MunicipioLocal municipioLocal: local.municipios_locais){
             municipios = municipios+municipioLocal.municipio.nome+", ";
        }
        result.put("municipios", Json.toJson(municipios));
 
        return ok(result);
    }
    
    
}
