package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.arvores.*;
import play.libs.Json;   
import javax.persistence.PersistenceException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import com.avaje.ebean.*;


import models.*;

public class Arvores extends Controller {

	public static Result GO_HOME = redirect(routes.Application.index());
	
	public static Result index() {
	    return GO_HOME;
	}

	public static Result novo(long id){
		Local local = Local.find.byId(id);
		return ok( novo.render(local));
	}

	public static Result saveFile(String files, Long id){
		Local local = Local.find.byId(id);
		String linhas[] = files.split(",");
		int cont = linhas.length;
		String numParcela = "";
		Long idParcela = null;

		for(int i = 0; i < cont ; i++){
			String temp = linhas[i];
			String itens[] = temp.split(";");
			if(itens[0] != null){

				if(numParcela != itens[0]){

					Parcela parcela = new Parcela();
					parcela.local = local;
					parcela.numParcela = Long.valueOf(itens[1]);
					parcela.save();

					idParcela = parcela.id;

					Arvore arvore = new Arvore();
					arvore.parcela = parcela;
					arvore.numArvore = Long.valueOf(itens[1]);
					//arvore.dap = Long.valueOf(itens[2]);
					//arvore.altura = Long.valueOf(itens[3]);
					arvore.qtdBiomassaObs = itens[4];
					arvore.qtdCarbonoObs = itens[5];
					arvore.qtdVolumeObs = itens[6];
					arvore.save();

				}else{
					Arvore arvore = new Arvore();
					arvore.parcela = Parcela.find.byId(idParcela);
					arvore.numArvore = Long.valueOf(itens[1]);
					//arvore.dap = Long.valueOf(itens[2]);
					//arvore.altura = Long.valueOf(itens[3]);
					arvore.qtdBiomassaObs = itens[4];
					arvore.qtdCarbonoObs = itens[5];
					arvore.qtdVolumeObs = itens[6];
					arvore.save();
				}
			}
		}
		return ok("Success");
	}

	public static Result saveGrid(long id){
        Local local = Local.find.byId(id);
		JsonNode json = request().body().asJson();
		Long idParcela = null;
		String numParcela = "";

		List<SqlRow> variaveis = Variavel.findByLocal(id);
        
        for(JsonNode row : json){

        	if(numParcela != row.get("parcela").toString()){
        		Parcela parcela = new Parcela();
        		parcela.local = local;
        		parcela.numParcela = Long.valueOf(row.get("parcela").toString());
        		parcela.save();

        		idParcela = parcela.id;

        		Arvore arvore = new Arvore();
        		
        		arvore.parcela= parcela;
				arvore.qtdBiomassaObs = (row.get("biomassa").toString());
				arvore.qtdCarbonoObs = (row.get("carbono").toString());
				arvore.qtdVolumeObs = (row.get("volume").toString());
				arvore.save();
        	}
        	else{
				Arvore arvore = new Arvore();
				arvore.parcela = Parcela.find.byId(idParcela);
				arvore.qtdBiomassaObs = (row.get("biomassa").toString());
				arvore.qtdCarbonoObs = (row.get("carbono").toString());
				arvore.qtdVolumeObs = (row.get("volume").toString());
				arvore.save();

        	}
        }
		
            //return ok(Json.toJson("mensagem : sucesso"));
            return ok(Json.toJson(variaveis));
        }

}