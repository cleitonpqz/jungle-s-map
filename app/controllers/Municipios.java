package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.formacao.*;
import javax.persistence.PersistenceException;
import play.libs.Json;
import models.*;

public class Municipios extends Controller {
    
public static Result listarMunicipio(Long id){ 
        return ok(Json.toJson(Municipio.opcoesPorEstado(id)));
    }    

}
