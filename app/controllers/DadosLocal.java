package controllers;

import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import views.html.dados.*;
import javax.persistence.PersistenceException;

import models.*;

public class DadosLocal extends Controller {

	public static Result GO_HOME = redirect(routes.Application.index());
	
	public static Result index() {
	    return GO_HOME;
	}

	public static Result calcular(long id, int quemChamou){
                Local local = Local.find.byId(id);
                if(local.parcelas!=null){
                    Form<Local> localForm = form(Local.class).fill(local);
                    return ok(calcular.render(localForm));
                }else{
                    flash("error", "O Local não possui dados a ele vinculados.");
                    if(quemChamou==1){
                        return redirect(routes.Parcelas.novo(id));
                    }else{
                        return redirect(routes.Arvores.novo(id));
                    }
                     
                }
	}
        public static Result detalheBiomassa(Long id) {
         LocalDetalheBiomassa ldb = Local.find.byId(id).local_detalhe_biomassa;
         Form<LocalDetalheBiomassa> ldbForm = form(LocalDetalheBiomassa.class).fill(ldb);
         return ok(
            localDetalheBiomassa.render(ldbForm)
         );
        }
        public static Result detalheCarbono(Long id) {
         LocalDetalheCarbono ldc = Local.find.byId(id).local_detalhe_carbono;
         Form<LocalDetalheCarbono> ldcForm;
         ldcForm=form(LocalDetalheCarbono.class).fill(ldc);
           
        return ok(
            localDetalheCarbono.render(ldcForm)
        );
        }
        public static Result detalheVolume(Long id) {
         LocalDetalheVolume ldv = Local.find.byId(id).local_detalhe_volume;
         Form<LocalDetalheVolume> ldvForm = form(LocalDetalheVolume.class).fill(ldv);
         return ok(
                localDetalheVolume.render(ldvForm)
         );
        }
        
        
        public static Result fazerCalculo(Long id, int quemChamou, int tipoEstimativa) {
            Local local = Local.find.byId(id);
            int countEquacao=0;
            for(TrabalhoCientificoEquacao trabalhoEquacao: local.trabalho_cientifico.trabalho_cientifico_equacao){
              if(trabalhoEquacao.equacao==null ||trabalhoEquacao.equacao.equals("")){
                  countEquacao++;
              }
              
            }
            if(countEquacao==2){
            EstimativaPorArvore estimativaPorArvore = new EstimativaPorArvore();
            EstimativaPorParcela estimativaPorParcela = new EstimativaPorParcela();
                switch(tipoEstimativa){
                    case 1: 
                       for(Parcela parcela : local.parcelas){
                            estimativaPorArvore.calculaEstimativaPorArvore(parcela);
                        }  
                    case 2:    
                        estimativaPorParcela.calculaEstimativaPorParcela(local);
                       break;
                     
                }           
                
            }else{
                flash("error", "Não todas as equações necessárias");
                return redirect(routes.DadosLocal.calcular(id, quemChamou));
            }
          return redirect(routes.DadosLocal.calcular(id, quemChamou));
        }
        
        
}