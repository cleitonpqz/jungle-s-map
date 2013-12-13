package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.modelo.*;
import org.nfunk.jep.*;
import javax.persistence.PersistenceException;
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
        Form<Equacao> equacaoForm = form(Equacao.class);
        return ok(
            cadastrar.render(varialvelInteresse ,equacaoForm)
            );
    }
    public static Result salvar(Long varialvelInteresse) {
       Form<Equacao> equacaoForm = form(Equacao.class).bindFromRequest();
        
        if(equacaoForm.hasErrors()) {
            flash("error","Modelo não incluído");
            return badRequest(cadastrar.render(varialvelInteresse, equacaoForm));
        }
        equacaoForm.get().save();
        flash("success", "O Modelo foi incluido com sucesso");
        return GO_HOME;
    }
    public static Result ajustar(Long idLocal, Long idEquacao, Integer tipoEstimativa){
        Equacao equacao = Equacao.find.byId(idEquacao);
        List<String> campos = new ArrayList<String>();
        campos.add("Árvore");
        for (EquacaoVariavel equacaoVariavel : equacao.equacao_variavel){
            campos.add(equacaoVariavel.variavel.sigla);
        }
         campos.add("Valor Observado");
         return ok(ajustar.render(equacao, campos, idLocal, tipoEstimativa));
    }
    
     public static Result salvarAjax(Long varialvelInteresse) {
		ObjectNode result = Json.newObject();
        Form<Equacao> equacaoForm = form(Equacao.class).bindFromRequest();
        if(form().bindFromRequest().get("autor_modelo.id")==null 
            || form().bindFromRequest().get("autor_modelo.id").equals("")) {
                	result.put("error", "Campo autor é obrigatório.");
			return ok(result);
        }
        if(form().bindFromRequest().get("expressao_modelo")==null 
            || form().bindFromRequest().get("expressao_modelo").equals("")) {
            		result.put("error", "Campo modelo é obrigatório.");
			return ok(result);
        }
        equacaoForm.get().save();
        Equacao equacao = equacaoForm.get();
		result.put("id", equacao.id);
		result.put("expressao_modelo", equacao.expressao_modelo);
		result.put("error", "null");
        return ok(result);
        
    }
     
     public static Result cadastrarModal(Long variavelInteresse) {
        Form<Equacao> equacaoForm = form(Equacao.class);
        return ok(
            novo.render(variavelInteresse ,equacaoForm, 0)
        );
    }
    
    public static Result fazerAjuste(Long idLocal, Long idEquacao, Integer estimativa){
        //Pegando os dados
        Equacao equacao = Equacao.find.byId(idEquacao);
        Local local = Local.find.byId(idLocal);
        EstatisticaCalculos calculaEstatistica = new EstatisticaCalculos();
        Estatistica estatistica = new Estatistica();
        JsonNode json = request().body().asJson();
        List<JsonNode> linhasPreenchidas =new ArrayList<JsonNode>();
        List<String> campos = new ArrayList<String>();
        campos.add("Árvore");
        for (EquacaoVariavel equacaoVariavel : equacao.equacao_variavel){
            campos.add(equacaoVariavel.variavel.sigla);
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
                return badRequest(ajustar.render(equacao, campos, idLocal, estimativa));   
            }else if(colunasPreenchidas==numeroColunas){
                linhasPreenchidas.add(row);
                numlinhasPreenchidas++;
            }
       }
            
        if(numlinhasPreenchidas<1){
            return badRequest(ajustar.render(equacao, campos, idLocal, estimativa));  
        }
        //Calculando
        int qtd_termos=0;
        for(Termo termo : equacao.termos){
            if(!termo.expressao.equals("1")){
                qtd_termos++;
            }
        }
        double[][] valorEntrada= new double[linhasPreenchidas.size()][qtd_termos];
        double[] valorObservado= new double[linhasPreenchidas.size()];
        for(int linha =0; linha<linhasPreenchidas.size(); linha++){
            ArvoreAjuste arvoreAjuste = new ArvoreAjuste();
            arvoreAjuste.local = local;
            int numTermo = qtd_termos;
             for(Termo termo : equacao.termos){
              if(!termo.expressao.equals("1")){
                JEP myParser = new JEP();
                myParser.addStandardFunctions();
                myParser.addStandardConstants();
                for (EquacaoVariavel equacaoVariavel : equacao.equacao_variavel){
                  Double valor = Double.parseDouble(linhasPreenchidas.get(linha).get(equacaoVariavel.variavel.sigla).toString());
                  myParser.addVariable(equacaoVariavel.variavel.sigla, valor);
                }
                myParser.parseExpression(termo.expressao);
                valorEntrada[linha][numTermo-1]=myParser.getValue();
                numTermo--;
              }
            }
             for (EquacaoVariavel equacaoVariavel : equacao.equacao_variavel){
                      ArvoreAjusteVariavel variavelAjuste = new ArvoreAjusteVariavel();
                      variavelAjuste.variavel = equacaoVariavel.variavel;
                      variavelAjuste.valor  = Double.parseDouble(linhasPreenchidas.get(linha).get(equacaoVariavel.variavel.sigla).toString());
                      arvoreAjuste.arvore_ajuste_variavel.add(variavelAjuste);
              }
             
            valorObservado[linha]= Double.parseDouble(linhasPreenchidas.get(linha).get("Valor Observado").toString());
            arvoreAjuste.equacao_id=equacao.id;
             if(equacao.variavel_interesse.id==Long.valueOf(1)){
                arvoreAjuste.qtd_biomassa_obs=valorObservado[linha];
            }else if(equacao.variavel_interesse.id==Long.valueOf(2)){
                arvoreAjuste.qtd_carbono_obs=valorObservado[linha];
            }else{
                arvoreAjuste.qtd_volume_obs=valorObservado[linha];
            }
             arvoreAjuste.save();
        }
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();        
        regression.newSampleData(valorObservado, valorEntrada);
        double[] valorCoeficiente = regression.estimateRegressionParameters();
        String expressao = equacao.expressao_modelo;
        ObjectNode result = Json.newObject();
        for(Integer i=0; i<equacao.qtd_coeficientes; i++){
                expressao=expressao.replaceAll("b"+i.toString(), String.format("%.4f",valorCoeficiente[i]));
          
          expressao=expressao.replaceAll(",",".");
          result.put("b"+i.toString(), String.format("%.4f",valorCoeficiente[i]));
        }
        equacao.expressao = expressao;
        equacao.update();
        result.put("expressao", expressao);
        estatistica=calculaEstatistica.calculaEstatisticas(idLocal, equacao);
        result.put("r2", String.format("%.4f",estatistica.r2));
        result.put("r2Ajust", String.format("%.4f",estatistica.r2Ajust));
        result.put("syx", String.format("%.4f",estatistica.syx));
                
        return ok(result);
    }
    public static boolean isNegative(Double number){
        if( number < 0){
            return true;
        }else {
            return false;
        }
 }

   
    
    
}
