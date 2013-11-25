package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.arvores.*;
import javax.persistence.PersistenceException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;    

import models.*;

public class Arvores extends Controller {

	public static Result GO_HOME = redirect(routes.Application.index());
	
	public static Result index() {
	    return GO_HOME;
	}

	public static Result novo(long id){
		return ok( novo.render(id));
	}

	public static Result saveFile(String files){
		
		String linhas[]= files.split(",");
		for(String cont: linhas){
			String items[] = cont.split(";");
		}
		
		return ok(linhas[0]);
	}

}