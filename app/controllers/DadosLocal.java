package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.dados.*;
import javax.persistence.PersistenceException;

import models.*;

public class DadosLocal extends Controller {

	public static Result GO_HOME = redirect(routes.Application.index());
	
	public static Result index() {
	    return GO_HOME;
	}

	public static Result calcular(long id){
                Local local = Local.find.byId(id);
		return ok( calcular.render(local));
	}

}