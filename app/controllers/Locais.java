package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.local.*;
import javax.persistence.PersistenceException;
import play.libs.Json.*;
import play.libs.Json;   
import static play.libs.Json.toJson;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;    
import models.*;

public class Locais extends Controller{
    
    public static Result GO_HOME = redirect(routes.Application.index());
    
       
    public static Result incluir() {
         Form<Local> localForm = form(Local.class);
         return ok(incluir.render(localForm));
    }
     
     public static Result listarFormacao(Long id){ 
        return ok(Json.toJson(Formacao.opcoesPorBioma(id)));
    }
     
     public static Result opcoesFormacao(){ 
        return ok(Json.toJson(Formacao.opcoes()));
    }
    
    public static Result  salvar() {
        Form<Local> localForm = form(Local.class).bindFromRequest();

       //validações manuais
         if(form().bindFromRequest().get("trabalho_cientifico.id")==null 
             || form().bindFromRequest().get("trabalho_cientifico.id").equals("")) {
             localForm.reject("trabalho_cientifico.id", "O campo Trabalho Científico é de preenchimento obrigatório!");
         }
         if(form().bindFromRequest().get("municipios_locais")==null 
             || form().bindFromRequest().get("municipios_locais[0].municipio.id").equals("")) {
             localForm.reject("municipios_locais[0].municipio.id", "O campo Município é de preenchimento obrigatório!");
         }
         if(form().bindFromRequest().get("espacamento.id")==null 
             || form().bindFromRequest().get("espacamento.id").equals("")) {
             localForm.reject("espacamento.id", "O campo Espaçamento é de preenchimento obrigatório!");
         }
         if(form().bindFromRequest().get("formacao.id")==null 
             || form().bindFromRequest().get("formacao.id").equals("")) {
             localForm.reject("formacao.id", "O campo Formação é de preenchimento obrigatório!");
         }
         //if(localForm.field("coordenadas")==null ) {
           //  localForm.reject("coordenadas", "O campo Coordenadas é de preenchimento obrigatório!");
        //}

        if(localForm.hasErrors()) {
           flash("error", "O Local não foi incluido com sucesso");
           return badRequest(incluir.render(localForm));
        }
        localForm.get().save();
        flash("success", "O Local " + localForm.get().descricao + " foi incluido com sucesso");
        return GO_HOME;
       }
    
    public static Result localizarSimilar(int page, String sortBy, String order, int bioma, int formacao, String local, int estado, int cidade) {
        return ok(
            popUpLocalizarSimilar.render(
                Local.localizarSimilar(page, 5, sortBy, order, bioma, formacao,local, estado, cidade) ,
                sortBy, order, bioma, formacao, local, estado, cidade)
            );
    }
    
    public static Result manter(int page, String sortBy, String order, int bioma, int formacao, String local, int estado, int cidade) {
        return ok(
            manter.render(
                Local.localizarSimilar(page, 5, sortBy, order, bioma, formacao,local, estado, cidade) ,
                sortBy, order, bioma, formacao, local, estado, cidade)
            );
    }
    
   
    public static Result excluir (Long id) {
        Result VOLTAR = redirect(routes.Locais.manter(0,"descricao", "asc", -1, -1, "", -1,-1));
        try{Local.find.ref(id).delete();
            flash("success", "Local excluido");
            return VOLTAR;
            }catch(PersistenceException exception){
             flash("error", "Exclusão não permitida");
             return VOLTAR;
            }
    }
    public static Result editar(Long id) {
         Local local = Local.find.byId(id);
         Form<Local> localForm = form(Local.class).fill(local);
         return ok(
            editarForm.render(id, localForm)
         );
    }
        
    public static Result atualizar(Long id) {
        Result VOLTAR = redirect(routes.Locais.manter(0,"descricao", "asc", -1, -1, "", -1,-1));
        Form<Local> localForm = form(Local.class).bindFromRequest();
        
        
        if(form().bindFromRequest().get("descricao")==null 
            || form().bindFromRequest().get("descricao").equals("")) {
             localForm.reject("trabalho_cientifico.id", "O campo Trabalho Científico é de preenchimento obrigatório!");
             return badRequest(editarForm.render(id, localForm));
         }
        
        //validações manuais
         if(form().bindFromRequest().get("trabalho_cientifico.id")==null 
             || form().bindFromRequest().get("trabalho_cientifico.id").equals("")) {
             localForm.reject("trabalho_cientifico.id", "O campo Trabalho Científico é de preenchimento obrigatório!");
         }
         if(form().bindFromRequest().get("municipios_locais")==null 
             || form().bindFromRequest().get("municipios_locais[0].municipio.id").equals("")) {
             localForm.reject("municipios_locais[0].municipio.id", "O campo Município é de preenchimento obrigatório!");
         }
         if(form().bindFromRequest().get("espacamento.id")==null 
             || form().bindFromRequest().get("espacamento.id").equals("")) {
             localForm.reject("espacamento.id", "O campo Espaçamento é de preenchimento obrigatório!");
         }
        if(form().bindFromRequest().get("formacao.id")==null 
            || form().bindFromRequest().get("formacao.id").equals("")) {
            localForm.reject("formacao.id", "O campo Formação é de preenchimento obrigatório!");
        }
        // if(form().bindFromRequest().get("coordenadas")==null ) {
        //     localForm.reject("coordenadas", "O campo Coordenadas é de preenchimento obrigatório!");
        // }


        if(localForm.hasErrors()) {
            return badRequest(editarForm.render(id, localForm));
        }
        localForm.get().update(id);
        flash("success", "O Local " + localForm.get().descricao + " foi alterado com sucesso");
        return VOLTAR;
    }
    
     public static Result findById(Long id) {
        ObjectNode result = Json.newObject();
        ObjectNode municipio = Json.newObject();
        String coordenadas ="";
        String municipios="";
        Local local = Local.find.byId(id);
        result.put("id", local.id);
	result.put("descricao", local.descricao);
        result.put("area_total", local.area_total);
        if(local.trabalho_cientifico!=null){
            result.put("auto", Json.toJson(local.trabalho_cientifico.autor));
            result.put("ano", Json.toJson(local.trabalho_cientifico.ano));
            result.put("disponibilidade", Json.toJson(local.trabalho_cientifico.disponibilidade));
            result.put("metodo_quantificacao_biomassa", Json.toJson(local.trabalho_cientifico.metodo_quantificacao_biomassa.descricao));
            result.put("metodo_quantificacao_carbono", Json.toJson(local.trabalho_cientifico.metodo_quantificacao_carbono.descricao));
            for(TrabalhoCientificoEquacao equacaoTrabalho : local.trabalho_cientifico.trabalho_cientifico_equacao){
                if(equacaoTrabalho.equacao.variavel_interesse.id==Long.valueOf(1)){
                   if(equacaoTrabalho.equacao.expressao!=null)
                        result.put("equacaoBiomassa", Json.toJson(equacaoTrabalho.equacao.expressao));
                   else
                       result.put("equacaoBiomassa", Json.toJson(""));
                   if(equacaoTrabalho.equacao.expressao_modelo!=null)
                        result.put("modeloBiomassa", Json.toJson(equacaoTrabalho.equacao.expressao_modelo));
                   else
                       result.put("modeloBiomassa", Json.toJson(""));
                }
                if(equacaoTrabalho.equacao.variavel_interesse.id==Long.valueOf(2)){
                    if(equacaoTrabalho.equacao.expressao!=null)
                        result.put("equacaoCarbono", Json.toJson(equacaoTrabalho.equacao.expressao));
                     else
                       result.put("equacaoCarbono", Json.toJson(""));
                    if(equacaoTrabalho.equacao.expressao_modelo!=null)                        
                        result.put("modeloCarbono", Json.toJson(equacaoTrabalho.equacao.expressao_modelo));
                     else
                       result.put("modeloCarbono", Json.toJson(""));
                }
                if(equacaoTrabalho.equacao.variavel_interesse.id==3){
                    if(equacaoTrabalho.equacao.expressao!=null)
                        result.put("equacaoVolume", Json.toJson(equacaoTrabalho.equacao.expressao));
                    else
                       result.put("equacaoVolume", Json.toJson(""));
                    if(equacaoTrabalho.equacao.expressao_modelo!=null)
                        result.put("modeloVolume", Json.toJson(equacaoTrabalho.equacao.expressao_modelo));
                    else
                       result.put("modeloVolume", Json.toJson(""));
                }
            }
        }
        result.put("formacao", Json.toJson(local.formacao));
	result.put("espacamento", Json.toJson(local.espacamento.descricao));
        result.put("area_total", local.area_total);
	for(Coordenada coordenada: local.coordenadas){
             coordenadas = coordenadas+"("+coordenada.latitude+","+coordenada.longitude+")";
        }
        result.put("coordenadas", Json.toJson(coordenadas));
        
        for(MunicipioLocal municipioLocal: local.municipios_locais){
            if(municipioLocal.principal!=null)
                municipios = municipios+municipioLocal.municipio.nome+"(principal), ";
            else
                municipios = municipios+municipioLocal.municipio.nome+", ";
        }
        result.put("municipios", Json.toJson(municipios));
        if(local.qtde_biomassa!=null){
            result.put("qtde_biomassa", String.format("%.4f",local.qtde_biomassa));
        }else{
            result.put("qtde_biomassa", "");
        }
        if(local.qtde_carbono!=null){
            result.put("qtde_carbono", String.format("%.4f",local.qtde_carbono));
        }else{
            result.put("qtde_carbono", "");
        }
        if(local.qtde_volume!=null){
            result.put("qtde_volume", String.format("%.4f",local.qtde_volume));
        }else{
            result.put("qtde_volume", "");
        }
        
        if(local.trabalho_cientifico.trabalho_cientifico_equacao.get(0) != null){
            for(TrabalhoCientificoEquacao trabalhoEquacao : local.trabalho_cientifico.trabalho_cientifico_equacao){
                if(trabalhoEquacao.equacao.variavel_interesse.id == 1){
                    if(trabalhoEquacao.equacao.expressao!=null && !trabalhoEquacao.equacao.expressao.equals("")){
                        result.put("equacao_biomassa", trabalhoEquacao.equacao.expressao);
                    }else result.put("equacao_biomassa", "Não possui equação cadastrada");
                    if(trabalhoEquacao.equacao.expressao_modelo!=null && !trabalhoEquacao.equacao.expressao_modelo.equals("")){
                        result.put("modelo_biomassa", trabalhoEquacao.equacao.expressao_modelo);
                    }else result.put("modelo_biomassa", "Não possui modelo cadastrado");
                }
                if(trabalhoEquacao.equacao.variavel_interesse.id == 2){
                    if(trabalhoEquacao.equacao.expressao!=null && !trabalhoEquacao.equacao.expressao.equals("")){
                        result.put("equacao_carbono", trabalhoEquacao.equacao.expressao);
                    }else result.put("equacao_carbono", "Não possui equação cadastrada");
                    if(trabalhoEquacao.equacao.expressao_modelo!=null && !trabalhoEquacao.equacao.expressao_modelo.equals("")){
                        result.put("modelo_carbono", trabalhoEquacao.equacao.expressao_modelo);
                    }else result.put("modelo_carbono", "Não possui modelo cadastrado");
                }
                if(trabalhoEquacao.equacao.variavel_interesse.id == 3){
                    if(trabalhoEquacao.equacao.expressao!=null && !trabalhoEquacao.equacao.expressao.equals("")){
                        result.put("equacao_volume", trabalhoEquacao.equacao.expressao);
                    }else result.put("equacao_volume", "Não possui equação cadastrada");
                    if(trabalhoEquacao.equacao.expressao_modelo!=null && !trabalhoEquacao.equacao.expressao_modelo.equals("")){
                        result.put("modelo_volume", trabalhoEquacao.equacao.expressao_modelo);
                    }else result.put("modelo_volume", "Não possui modelo cadastrado");
                }
            }
         }
 
        return ok(result);
    }
    
    
}
