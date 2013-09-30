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
            
                // Routes for Locais
                controllers.routes.javascript.Locais.incluir(), 
                controllers.routes.javascript.Locais.listarFormacao(),
                controllers.routes.javascript.Locais.salvarModal()
                             
                
            )
        );
    }
  
}
