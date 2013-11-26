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
					arvore.dap = Long.valueOf(itens[2]);
					arvore.altura = Long.valueOf(itens[3]);
					arvore.qtdBiomassaObs = itens[4];
					arvore.qtdCarbonoObs = itens[5];
					arvore.qtdVolumeObs = itens[6];
					arvore.save();

				}else{
					Arvore arvore = new Arvore();
					arvore.parcela = Parcela.find.byId(idParcela);
					arvore.numArvore = Long.valueOf(itens[1]);
					arvore.dap = Long.valueOf(itens[2]);
					arvore.altura = Long.valueOf(itens[3]);
					arvore.qtdBiomassaObs = itens[4];
					arvore.qtdCarbonoObs = itens[5];
					arvore.qtdVolumeObs = itens[6];
					arvore.save();
				}
			}
		}
		return ok("Success");
	}

}