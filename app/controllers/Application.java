package controllers;

import java.util.*;
import play.*;
import play.mvc.*;
import models.*;
import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        List<Local> locais = Local.find.all();
        return ok(index.render(locais));
    }
    
    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
            Routes.javascriptRouter("jsRoutes",
            
                controllers.routes.javascript.Locais.incluir(), 
                controllers.routes.javascript.Formacoes.listarFormacao(),
                controllers.routes.javascript.Municipios.listarMunicipio(),
                controllers.routes.javascript.TrabalhosCientificos.findById(),
                controllers.routes.javascript.Locais.findById(),
                controllers.routes.javascript.Modelos.fazerAjuste(),
                controllers.routes.javascript.Arvores.saveFile(),
                controllers.routes.javascript.Parcelas.saveFile(),
                controllers.routes.javascript.Parcelas.saveGrid()             
                
            )
        );
    }
  
}
