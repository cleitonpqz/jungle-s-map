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
import java.io.FileWriter;


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
		
		List<SqlRow> variaveis = Variavel.findByLocal(id);

		for(int i = 0; i < cont ; i++){
			String temp = linhas[i];
			String itens[] = temp.split(";");
			if(itens[0] != null){

				if(numParcela != itens[0]){

					Parcela parcela = new Parcela();
					
					parcela.local = local;
					parcela.numParcela = Long.valueOf(itens[0]);
					parcela.save();

					idParcela = parcela.id;

					Arvore arvore = new Arvore();
					arvore.parcela = parcela;

					arvore.numArvore = Long.valueOf(itens[1]);
					arvore.qtdBiomassaObs = itens[2];
					arvore.qtdCarbonoObs = itens[3];
					arvore.qtdVolumeObs = itens[4];
					int x = 5;
					for(SqlRow l : variaveis){
						Variavel var = Variavel.find.byId(Long.valueOf(l.getString("id")));
						VariavelArvore varA = new VariavelArvore();
						varA.valor = Long.valueOf(itens[x]);
						varA.variavel = var;
						arvore.variavelArvore.add(varA);
						x ++; 
					}
					arvore.save();

				}else{
					Arvore arvore = new Arvore();
					arvore.parcela = Parcela.find.byId(idParcela);

					arvore.numArvore = Long.valueOf(itens[1]);
					arvore.qtdBiomassaObs = itens[2];
					arvore.qtdCarbonoObs = itens[3];
					arvore.qtdVolumeObs = itens[4];
					int x = 5;
					for(SqlRow l : variaveis){
						Variavel var = Variavel.find.byId(Long.valueOf(l.getString("id")));
						VariavelArvore varA = new VariavelArvore();
						varA.valor = Long.valueOf(itens[x]);
						varA.variavel = var;
						arvore.variavelArvore.add(varA);
						x ++; 
					}

					arvore.save();
				}
			}
		}
		return ok("message : success");
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
        		parcela.num_parcela = Long.valueOf(row.get("parcela").toString());
        		parcela.save();

        		idParcela = parcela.id;

        		Arvore arvore = new Arvore();
        		
        		arvore.parcela= parcela;
        		arvore.num_arvore = Long.valueOf(row.get("arvore").toString());
				arvore.qtd_biomassa_obs = Double.parseDouble((row.get("biomassa").toString()));
				arvore.qtd_carbono_obs = Double.parseDouble((row.get("carbono").toString()));
				arvore.qtd_volume_obs = Double.parseDouble((row.get("volume").toString()));
				for(SqlRow l: variaveis){

					Variavel var = Variavel.find.byId(Long.valueOf(l.getString("id")));
					VariavelArvore varA = new VariavelArvore();
					varA.valor = Double.valueOf(row.get(l.getString("id")).toString());
					varA.variavel = var;
					arvore.variavel_arvore.add(varA); 
				}
				
				arvore.save();
        	}
        	else{
				Arvore arvore = new Arvore();
				arvore.parcela = Parcela.find.byId(idParcela);
				arvore.num_arvore = Long.valueOf(row.get("arvore").toString());
				arvore.qtd_biomassa_obs = Double.parseDouble((row.get("biomassa").toString()));
				arvore.qtd_carbono_obs = Double.parseDouble((row.get("carbono").toString()));
				arvore.qtd_volume_obs = Double.parseDouble((row.get("volume").toString()));
				for(SqlRow l: variaveis){

					Variavel var = Variavel.find.byId(Long.valueOf(l.getString("id")));
					VariavelArvore varA = new VariavelArvore();
					varA.valor = Double.valueOf(row.get(l.getString("id")).toString());
					varA.variavel = var;
					arvore.variavel_arvore.add(varA); 
				}
				arvore.save();

        	}
        }
       
        
		
            return ok(Json.toJson("mensagem : sucesso"));
            //return ok(Json.toJson(teste));
        }

        public static Result criaModelo(Long id){
        	List<SqlRow> variaveis = Variavel.findByLocal(id);
        	String modelo = "Parcela;Arvore;Biomassa;Carbono;Volume";
        	for(SqlRow l : variaveis){
        		modelo = modelo + ";" + l.getString("sigla");
        	}
        	try{
        		FileWriter arvore = new FileWriter("arvores.csv", false);

        		arvore.write(modelo);
        		arvore.close();
	
        	}
        	catch (Exception e){
        		return ok("error");
        	}
        	
        	response().setContentType("application/x-download");  
			response().setHeader("Content-disposition","attachment; filename=tradeLogTest.csv");
        	
        	return ok(new java.io.File("arvores.csv"));
        }

}