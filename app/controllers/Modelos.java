package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.modelo.*;
import org.nfunk.jep.*;
import javax.persistence.PersistenceException;

import models.*;

public class Modelos extends Controller {
public static Result GO_HOME = redirect(routes.TrabalhosCientificos.manter(0, "nome", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result cadastrar(Long varialvelInteresse) {
        Form<Modelo> modeloForm = form(Modelo.class);
        return ok(
            cadastrar.render(varialvelInteresse ,modeloForm)
            );
    }
    public static Result salvar(Long varialvelInteresse) {
       Form<Modelo> modeloForm = form(Modelo.class).bindFromRequest();
        
        if(modeloForm.hasErrors()) {
            flash("error","Modelo não incluído");
            return badRequest(cadastrar.render(varialvelInteresse, modeloForm));
        }
        modeloForm.get().save();
        flash("success", "O Modelo foi incluido com sucesso");
        return GO_HOME;
    }
    public static Result ajustar(Long idModelo){
        Modelo modelo = Modelo.find.byId(idModelo);
        List campos = new ArrayList();
        for (ModeloVariavel modeloVariavel : modelo.modelo_variavel){
            campos.add(modeloVariavel.variavel.sigla);
        }
         campos.add(modelo.variavel_interesse.nome +" Observada");
         JEP myParser = new org.nfunk.jep.JEP();
         myParser.addStandardFunctions();
         myParser.addStandardConstants();
         String equacao = "(DAP^2+H)+36";
         myParser.addVariable("DAP",2);
         myParser.addVariable("H",3);
         myParser.parseExpression(equacao);
         System.out.println(myParser.getValue());
         return ok(ajustar.render(modelo, campos));
    }
    
    public static boolean isVariavelNotNull(String variavel){  
    return (variavel!=null && !variavel.equals(""));  
} 

}
