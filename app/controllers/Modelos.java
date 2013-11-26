package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.modelo.*;
import org.nfunk.jep.*;
import javax.persistence.PersistenceException;
import play.libs.Json.*;
import play.libs.Json;   
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import models.*;

public class Modelos extends Controller {
public static Result GO_HOME = redirect(routes.TrabalhosCientificos.manter(0, "nome", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result cadastrar(Long varialvelInteresse) {
        Form<Modelo> modeloForm = form(Modelo.class);
        return ok(
            cadastrar.render(varialvelInteresse ,modeloForm)
            );
    }
    public static Result salvar(Long varialvelInteresse) {
       Form<Modelo> modeloForm = form(Modelo.class).bindFromRequest();
        
        if(modeloForm.hasErrors()) {
            flash("error","Modelo não incluído");
            return badRequest(cadastrar.render(varialvelInteresse, modeloForm));
        }
        modeloForm.get().save();
        flash("success", "O Modelo foi incluido com sucesso");
        return GO_HOME;
    }
    public static Result ajustar(Long idModelo){
        Modelo modelo = Modelo.find.byId(idModelo);
        List<String> campos = new ArrayList<String>();
        campos.add("Árvore");
        for (ModeloVariavel modeloVariavel : modelo.modelo_variavel){
            campos.add(modeloVariavel.variavel.sigla);
        }
         campos.add("Valor Observado");
         return ok(ajustar.render(modelo, campos));
    }
    
    public static Result fazerAjuste(Long idModelo){
        //Pegando os dados
        Modelo modelo = Modelo.find.byId(idModelo);
        JsonNode json = request().body().asJson();
        List<JsonNode> linhasPreenchidas =new ArrayList<JsonNode>();
        List<String> campos = new ArrayList<String>();
        campos.add("Árvore");
         for (ModeloVariavel modeloVariavel : modelo.modelo_variavel){
            campos.add(modeloVariavel.variavel.sigla);
        }
        campos.add("Valor Observado");
        //validando dados da grid
        int numeroColunas = campos.size();
        int numlinhasPreenchidas =0;
        for(JsonNode row : json){
            int colunasPreenchidas = 0;
            for(int j=0; j<numeroColunas; j++){
                String aux = campos.get(j);
                JsonNode celula = row.get(aux);
                if(celula!=null){
                    if(!celula.toString().equals("\"\"")){
                        colunasPreenchidas++; }  
                }
            } 
            if(colunasPreenchidas > 0 && colunasPreenchidas< numeroColunas){
                return badRequest(ajustar.render(modelo, campos));   
            }else if(colunasPreenchidas==numeroColunas){
                linhasPreenchidas.add(row);
                numlinhasPreenchidas++;
            }
       }
            
        if(numlinhasPreenchidas<1){
            return badRequest(ajustar.render(modelo, campos));  
        }
        //Calculando
        double[][] valorEntrada= new double[linhasPreenchidas.size()][modelo.termos.size()];
        double[] valorObservado= new double[linhasPreenchidas.size()];
        for(int linha =0; linha<linhasPreenchidas.size(); linha++){
            int numTermo = 0;
             for(Termo termo : modelo.termos){
                JEP myParser = new JEP();
                myParser.addStandardFunctions();
                myParser.addStandardConstants();
                for (ModeloVariavel modeloVariavel : modelo.modelo_variavel){
                  Double valor = Double.parseDouble(linhasPreenchidas.get(linha).get(modeloVariavel.variavel.sigla).toString());
                  myParser.addVariable(modeloVariavel.variavel.sigla, valor);
                }
                myParser.parseExpression(termo.expressao);
                valorEntrada[linha][numTermo]=myParser.getValue();
                numTermo++;
            }
            valorObservado[linha]= Double.parseDouble(linhasPreenchidas.get(linha).get("Valor Observado").toString());
        }
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();        
        regression.newSampleData(valorObservado, valorEntrada);
        double[] valorCoeficiente = regression.estimateRegressionParameters();
        
        Equacao novaEquacao = new Equacao();
        String expressao = modelo.expressao;
        ObjectNode result = Json.newObject();
        for(Integer i=0; i<modelo.qtd_coeficientes; i++){
          expressao=expressao.replaceAll("b"+i.toString(), String.valueOf(valorCoeficiente[i]));
          result.put("b"+i.toString(), String.valueOf(valorCoeficiente[i]));
        }
        result.put("expressao", expressao);
        
        return ok(result);
    }
    
    
}
