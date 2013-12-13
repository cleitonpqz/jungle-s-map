package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.trabalho_cientifico.*;
import javax.persistence.PersistenceException;
import play.libs.Json;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import models.*;

public class TrabalhosCientificos extends Controller {
    
    public static Result GO_HOME = redirect(routes.TrabalhosCientificos.manter(0, "autor.nome", "asc", ""));
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result manter(int page, String sortBy, String order, String filter) {
           return ok(
            manter.render(
                TrabalhoCientifico.page(page, 10, sortBy, order, filter),sortBy, order, filter)
            );
    }
    
     public static Result incluirEditar(Long id) {
         Form<TrabalhoCientifico> trabalhoCientificoForm;
         if(id!=0){
                trabalhoCientificoForm=form(TrabalhoCientifico.class).fill( TrabalhoCientifico.find.byId(id));
         }else{
                trabalhoCientificoForm = form(TrabalhoCientifico.class);
        }
                
        return ok(
            editarForm.render(id, trabalhoCientificoForm)
        );
    }
    
    public static Result salvar(Long id) {
        Form<TrabalhoCientifico> trabalhoCientificoForm = form(TrabalhoCientifico.class).bindFromRequest();
        if(form().bindFromRequest().get("autor.id")==null 
             || form().bindFromRequest().get("autor.id").equals("")) {
             trabalhoCientificoForm.reject("autor.id", "O campo autor é de preenchimento obrigatório!");
         }
        if(form().bindFromRequest().get("disponibilidade.id")==null 
             || form().bindFromRequest().get("disponibilidade.id").equals("")) {
             trabalhoCientificoForm.reject("disponibilidade.id", "O campo disponibilidade é de preenchimento obrigatório!");
         }
        if(form().bindFromRequest().get("metodo_quantificacao_biomassa.id")==null 
             || form().bindFromRequest().get("metodo_quantificacao_biomassa.id").equals("")) {
             trabalhoCientificoForm.reject("metodo_quantificacao_biomassa.id", "O campo quant. de Biomassa é de preenchimento obrigatório!");
         }
        
        if(form().bindFromRequest().get("ano")!=null && !form().bindFromRequest().get("ano").equals("")) {
            if(Integer.valueOf(form().bindFromRequest().get("ano"))<1800 ||  Integer.valueOf(form().bindFromRequest().get("ano"))>Calendar.getInstance().get(Calendar.YEAR)) {
                 trabalhoCientificoForm.reject("ano", "Ano inválido");
             }
        }    
        
        if(form().bindFromRequest().get("metodo_quantificacao_carbono.id")==null 
             || form().bindFromRequest().get("metodo_quantificacao_carbono.id").equals("")) {
             trabalhoCientificoForm.reject("metodo_quantificacao_carbono.id", "O campo quant. de Carbono é de preenchimento obrigatório!");
         }
                
        if(trabalhoCientificoForm.hasErrors()) {
            return badRequest(editarForm.render(id, trabalhoCientificoForm));
        }
        if(id!=0){
            trabalhoCientificoForm.get().update(id);
            flash("success", "O Trabalho Científico do autor " + trabalhoCientificoForm.get().autor.nome + " foi alterado com sucesso");
           
        }else{
             trabalhoCientificoForm.get().save();
             flash("success", "O Trabalho Científico do autor " + trabalhoCientificoForm.get().autor.nome + " foi incluído com sucesso");
        
        }
        
         return GO_HOME;
    }
      
    
    public static Result deletar(Long id) {
        try{
            TrabalhoCientifico.find.ref(id).delete();
            flash("success", "Trabalho Científico excluído com sucesso");
            return GO_HOME;
        }catch(PersistenceException exception){
            flash("error", "Exclusão não permitida. Existe registros vinculados a este Trabalho Científico");
        return GO_HOME;
        }   
    }
    
    public static Result findById(Long id) {
        TrabalhoCientifico trabalho = TrabalhoCientifico.find.byId(id);
        ObjectNode result = Json.newObject();
         result.put("id", trabalho.id);
         result.put("autor", Json.toJson(trabalho.autor));
         result.put("disponibilidade", Json.toJson(trabalho.disponibilidade));
         result.put("metodo_quantificacao_biomassa", Json.toJson(trabalho.metodo_quantificacao_biomassa));
         result.put("metodo_quantificacao_carbono", Json.toJson(trabalho.metodo_quantificacao_carbono));
         result.put("ano", trabalho.ano);
         if(trabalho.trabalho_cientifico_equacao.get(0) != null){
            for(TrabalhoCientificoEquacao trabalhoEquacao : trabalho.trabalho_cientifico_equacao){
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