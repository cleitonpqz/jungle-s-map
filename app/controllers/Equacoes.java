package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.equacao.*;
import java.sql.SQLException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;
import javax.persistence.PersistenceException;

import models.*;

public class Equacoes extends Controller {
public static Result GO_HOME = redirect(routes.TrabalhosCientificos.manter(0, "nome", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result cadastrar(Long varialvelInteresse) {
        Form<Equacao> equacaoForm = form(Equacao.class);
        return ok(
            cadastrarEquacao.render(varialvelInteresse ,equacaoForm)
            );
    }
    public static Result salvar(Long varialvelInteresse) {
       Form<Equacao> equacaoForm = form(Equacao.class).bindFromRequest();
         if(equacaoForm.hasErrors()) {
            flash("error","Equação não incluída");
            return badRequest(cadastrarEquacao.render(varialvelInteresse, equacaoForm));
        }
        equacaoForm.get().save();
        flash("success", "A Equação " + equacaoForm.get().expressao + " foi incluida com sucesso");
        return GO_HOME;
    }
    
    public static Result salvarAjax(Long varialvelInteresse) {
		ObjectNode result = Json.newObject();
        Form<Equacao> equacaoForm = form(Equacao.class).bindFromRequest();
        if(form().bindFromRequest().get("expressao")==null 
            || form().bindFromRequest().get("expressao").equals("")) {
            result.put("error", "Campo equação é obrigatório.");
			return ok(result);
        }
        
  		equacaoForm.get().save();
        Equacao equacao = equacaoForm.get();
		result.put("id", equacao.id);
		result.put("expressao", equacao.expressao);
		result.put("error", "null");
        return ok(result);
    }
    public static Result cadastrarModal(Long variavelInteresse) {
        Form<Equacao> equacaoForm = form(Equacao.class);
        return ok(
            novo.render(variavelInteresse ,equacaoForm, 0)
        );
    }
    
}
