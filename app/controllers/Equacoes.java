package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.equacao.*;
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
    public static Result salvar() {
       Form<Equacao> equacaoForm = form(Equacao.class).bindFromRequest();
        
        if(equacaoForm.hasErrors()) {
            return badRequest(cadastrarEquacao.render(Long.parseLong("3"), equacaoForm));
        }
        equacaoForm.get().save();
        flash("success", "O Equação " + equacaoForm.get().visualizacao + " foi incluida com sucesso");
        return GO_HOME;
    }
    

}
