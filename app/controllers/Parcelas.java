package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.parcelas.*;
import play.libs.Json;   
import org.codehaus.jackson.JsonNode;
import javax.persistence.PersistenceException;

import models.*;

public class Parcelas extends Controller {

	public static Result GO_HOME = redirect(routes.Application.index());
	
	public static Result index() {
	    return GO_HOME;
	}

	public static Result novo(long id){
		Local local = Local.find.byId(id);
		return ok( novo.render(local));
	}

	public static Result saveFile(String files, long id, Double areaParcela){
		Local local = Local.find.byId(id);
                local.area_parcela = areaParcela;
                local.update(id);
		String linhas[] = files.split(",");
		int cont = linhas.length;
		for(int i = 0; i < cont ; i++){
			String temp = linhas[i];
			String itens[] = temp.split(";");
			if(itens[0] != null){
				Parcela parcela = new Parcela();
				parcela.local = local;
				parcela.num_parcela = Long.valueOf(itens[0]);
				parcela.biomassa = Double.valueOf(itens[0]);
				parcela.carbono = Double.valueOf(itens[0]);
				parcela.volume = Double.valueOf(itens[0]);

				parcela.save();
			}

		}
		return ok("success");
	}
        
        public static Result saveGrid(long id, Double areaParcela){
                Local local = Local.find.byId(id);
                local.area_parcela = areaParcela;
                local.update(id);
		JsonNode json = request().body().asJson();
                for(JsonNode row : json){
                    Parcela parcela = new Parcela();
				parcela.local = local;
				parcela.num_parcela = Long.valueOf(row.get("parcela").toString());
				parcela.biomassa = Double.valueOf(row.get("biomassa").toString());
				parcela.carbono = Double.valueOf(row.get("carbono").toString());
				parcela.volume = Double.valueOf(row.get("volume").toString());

				parcela.save();
                }
		
            return ok(Json.toJson("mensagem : sucesso"));
        }

}
