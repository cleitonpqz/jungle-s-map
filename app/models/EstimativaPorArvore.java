package models;

import java.sql.SQLException;
import java.util.ArrayList;
import org.nfunk.jep.*;

public class EstimativaPorArvore {
    double mediaParcela = 0;
    double variancia = 0;
    double desvioPadrao = 0;
    double qtdeParcelasLocal = 0;
    double umMenosF = 0;
    double erroPadrao = 0;
    double coeficienteVariacao = 0;
    double varianciaMedia = 0;
    double erroAbsoluto = 0;
    double erroRelativo = 0;
    double intervaloConfiancaMinParcela = 0;
    double intervaloConfiancaMaxParcela = 0;
    double mediaLocal = 0;
    double t = 0;
    JEP parserBiomassa;
    JEP parserCarbono;
    JEP parserVolume;
            
    
    public void calculaEstimativaPorArvore(Parcela parcela) {

        double somaBiomassaEst = 0.0;
        double somaCarbonoEst = 0.0;
        double somaVolumeEst = 0.0;

        Local local = Local.find.byId(parcela.local.id);
        boolean retorno;
        Equacao equacaoBiomassa = new Equacao();
        Equacao equacaoCarbono = new Equacao();
        Equacao equacaoVolume = new Equacao();
        
        for(TrabalhoCientificoEquacao trabEquacao : local.trabalho_cientifico.trabalho_cientifico_equacao){
            Equacao equacaoAux = trabEquacao.equacao;
            switch(equacaoAux.variavel_interesse.id.intValue()){
                case(1):
                    equacaoBiomassa = equacaoAux;
                    break;
                case(2):
                    equacaoCarbono  = equacaoAux;
                    break;
                case(3):
                    equacaoVolume   = equacaoAux;
                    break;
            }
        }
        
        for(Arvore arvore : parcela.arvore) {
            
            parserBiomassa = new org.nfunk.jep.JEP();
            parserBiomassa.addStandardFunctions();
            parserBiomassa.addStandardConstants();
            
            parserCarbono = new org.nfunk.jep.JEP();
            parserCarbono.addStandardFunctions();
            parserCarbono.addStandardConstants();
            
            parserVolume = new org.nfunk.jep.JEP();
            parserVolume.addStandardFunctions();
            parserVolume.addStandardConstants();
            
                for(EquacaoVariavel variavelEquacao : equacaoBiomassa.equacao_variavel){
                    for(VariavelArvore variavelArvore : arvore.variavel_arvore)
                        if(variavelEquacao.variavel.id == variavelArvore.variavel.id){
                            parserBiomassa.addVariable(variavelEquacao.variavel.sigla, variavelArvore.valor);
                        }
                    }
                   parserBiomassa.parseExpression(equacaoBiomassa.expressao);
                    somaBiomassaEst =+parserBiomassa.getValue();
                    
                 for(EquacaoVariavel variavelEquacao : equacaoCarbono.equacao_variavel){
                    for(VariavelArvore variavelArvore : arvore.variavel_arvore)
                        if(variavelEquacao.variavel.id == variavelArvore.variavel.id){
                            parserCarbono.addVariable(variavelEquacao.variavel.sigla, variavelArvore.valor);
                        }
                    }
                    parserCarbono.parseExpression(equacaoCarbono.expressao);
                    somaCarbonoEst =+parserCarbono.getValue();
                    
                 for(EquacaoVariavel variavelEquacao : equacaoVolume.equacao_variavel){
                    for(VariavelArvore variavelArvore : arvore.variavel_arvore){
                            if(variavelEquacao.variavel.id == variavelArvore.variavel.id){
                                System.out.println("aquiiiiiiiiiiiiiiiiiiiiii");
                                parserVolume.addVariable(variavelEquacao.variavel.sigla, variavelArvore.valor);
                            }
                        }
                    }
                   parserVolume.parseExpression(equacaoVolume.expressao);
                   System.out.println(String.valueOf(somaVolumeEst));
                    somaVolumeEst =+parserVolume.getValue();
                    System.out.println(String.valueOf(somaVolumeEst));
                    
                           
        }        
        parcela.biomassa=somaBiomassaEst;
        parcela.carbono=somaCarbonoEst;
        parcela.volume=somaVolumeEst;
        System.out.println(String.valueOf(parcela.volume));
        parcela.update();
                
   }  
     
}
