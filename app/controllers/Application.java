package controllers;

import play.*;
import play.mvc.*;
import models.*;
import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
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
