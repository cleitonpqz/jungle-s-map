package models;

import java.sql.SQLException;
import java.util.ArrayList;
import org.nfunk.jep.*;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.distribution.TDistribution;


public class EstatisticaCalculos {
    
    public Estatistica calculaEstatisticas(Long idLocal, Equacao equacao){
        Double somaBiomassaEst = 0.0;
        Double somaCarbonoEst  = 0.0;
        Double somaVolumeEst   = 0.0;
        Double somaBiomassaObs = 0.0;
        Double somaCarbonoObs  = 0.0;
        Double somaVolumeObs   = 0.0;
        Double qtdeBiomassaObsMed = 0.0;
        Double qtdeCarbonoObsMed = 0.0;
        Double qtdeVolumeObsMed = 0.0;
        Double qtdeBiomassaObs = 0.0;
        Double qtdeCarbonoObs = 0.0;
        Double qtdeVolumeObs = 0.0;
        Double qtdeBiomassaEst = 0.0;
        Double qtdeCarbonoEst = 0.0;
        Double qtdeVolumeEst = 0.0;        
        Double somaQuadradoResiduoBiomassa   = 0.0;
        Double somaQuadradoResiduoCarbono   = 0.0;
        Double somaQuadradoResiduoVolume   = 0.0;
        Double somaQuadradoRegressaoBiomassa = 0.0;
        Double somaQuadradoRegressaoCarbono = 0.0;
        Double somaQuadradoRegressaoVolume = 0.0;        
        Double somaQuadradoTotaisBiomassa    = 0.0;
        Double somaQuadradoTotaisCarbono    = 0.0;
        Double somaQuadradoTotaisVolume    = 0.0;

        Double r2Biomassa = 0.0;
        Double r2Carbono = 0.0;
        Double r2Volume = 0.0;
        Double r2AjustBiomassa = 0.0;
        Double r2AjustCarbono = 0.0;
        Double r2AjustVolume = 0.0;
        Double iaBiomassa = 0.0;
        Double iaCarbono = 0.0;
        Double iaVolume = 0.0;
        Double syxBiomassa = 0.0;
        Double syxCarbono = 0.0;
        Double syxVolume = 0.0;
        Double syxPercBiomassa = 0.0;
        Double syxPercCarbono = 0.0;        
        Double syxPercVolume = 0.0;
        
        JEP parser; 
        Estatistica estatistica = new Estatistica();
        boolean retorno;
            Local local = Local.find.byId(idLocal);
            Integer qtdArvores = local.arvore_ajuste.size();
            
        switch(equacao.variavel_interesse.id.intValue()){
          case 1: 
                 for(ArvoreAjuste arvore : ArvoreAjuste.findArvoreAjuste(idLocal, equacao.id)){
                    somaBiomassaObs =+ arvore.qtd_biomassa_obs;
                    
                    parser = new org.nfunk.jep.JEP();
                    parser.addStandardFunctions();
                    parser.addStandardConstants();
                    for(EquacaoVariavel equacaoVariavel : equacao.equacao_variavel){
                        for(ArvoreAjusteVariavel arvoreAjusteVariavel : arvore.arvore_ajuste_variavel)
                            if(equacaoVariavel.variavel.id == arvoreAjusteVariavel.variavel.id){
                                parser.addVariable(equacaoVariavel.variavel.sigla, arvoreAjusteVariavel.valor);
                            }
                    }
                    parser.parseExpression(equacao.expressao);    
                    qtdeBiomassaEst = parser.getValue();
                        arvore.qtd_biomassa_est = qtdeBiomassaEst;
                        somaBiomassaEst = somaBiomassaEst + qtdeBiomassaEst;
                        arvore.update();
                
                    }
                  local = Local.find.byId(idLocal);
                  if (qtdArvores>0) {
                    qtdeBiomassaObsMed = somaBiomassaObs / qtdArvores;
                  } else {
                    qtdeBiomassaObsMed = 0.0;
                  }
                  for(ArvoreAjuste arvore : ArvoreAjuste.findArvoreAjuste(idLocal, equacao.id)){
                      qtdeBiomassaObs = arvore.qtd_biomassa_obs;
                      qtdeBiomassaEst = arvore.qtd_biomassa_est;
                      somaQuadradoResiduoBiomassa   =+ Math.pow((qtdeBiomassaObs - qtdeBiomassaEst), 2);
                      somaQuadradoRegressaoBiomassa =+ Math.pow((qtdeBiomassaEst - qtdeBiomassaObsMed), 2);
                      somaQuadradoTotaisBiomassa    =+ Math.pow((qtdeBiomassaObs - qtdeBiomassaObsMed), 2);
                  }
               if (qtdArvores>0){
                     r2Biomassa = somaQuadradoRegressaoBiomassa / somaQuadradoTotaisBiomassa;
                        if(qtdArvores>3) {
                            r2AjustBiomassa = 1 - (1-r2Biomassa) * ((qtdArvores - 1) / (qtdArvores - 2 - 1));
                        } else {
                            r2AjustBiomassa = 1 - (1-r2Biomassa) * ((qtdArvores - 1) / (qtdArvores - 1));
                            syxBiomassa     = Math.sqrt(somaQuadradoResiduoBiomassa / (qtdArvores));
                        }
                        //Syx% = (Syx/volumeObsMedio)*100
                        syxPercBiomassa = (syxBiomassa / qtdeBiomassaObsMed) * 100;
                        iaBiomassa = 1 - (somaQuadradoResiduoBiomassa/somaQuadradoTotaisBiomassa);
                        
                        estatistica.numLocal=idLocal;
                        estatistica.r2=r2Biomassa;
                        estatistica.r2Ajust=r2AjustBiomassa;
                        estatistica.ia=iaBiomassa;
                        estatistica.syx=syxBiomassa;
                        estatistica.syxPerc = syxPercBiomassa;
                }
               
              break;
          case 2:
                for(ArvoreAjuste arvore : ArvoreAjuste.findArvoreAjuste(idLocal, equacao.id)){
                    somaCarbonoObs =+ arvore.qtd_carbono_obs;
                    
                    parser = new org.nfunk.jep.JEP();
                    parser.addStandardFunctions();
                    parser.addStandardConstants();
                    for(EquacaoVariavel equacaoVariavel : equacao.equacao_variavel){
                        for(ArvoreAjusteVariavel arvoreAjusteVariavel : arvore.arvore_ajuste_variavel)
                            if(equacaoVariavel.variavel.id == arvoreAjusteVariavel.variavel.id){
                                parser.addVariable(equacaoVariavel.variavel.sigla, arvoreAjusteVariavel.valor);
                            }
                    }
                        parser.parseExpression(equacao.expressao);
                        qtdeCarbonoEst = parser.getValue();
                        arvore.qtd_carbono_est = qtdeCarbonoEst;
                        somaCarbonoEst = somaCarbonoEst + qtdeCarbonoEst;
                        arvore.update();
                
                    }
                    local = Local.find.byId(idLocal);
                  if (qtdArvores>0) {
                    qtdeCarbonoObsMed = somaCarbonoObs / qtdArvores;
                  } else {
                    qtdeCarbonoObsMed = 0.0;
                  }
                  for(ArvoreAjuste arvore : ArvoreAjuste.findArvoreAjuste(idLocal, equacao.id)){
                      qtdeCarbonoObs = arvore.qtd_carbono_obs;
                      qtdeCarbonoEst = arvore.qtd_carbono_est;
                      somaQuadradoResiduoCarbono   =+ Math.pow((qtdeCarbonoObs - qtdeCarbonoEst), 2);
                      somaQuadradoRegressaoCarbono =+ Math.pow((qtdeCarbonoEst - qtdeCarbonoObsMed), 2);
                      somaQuadradoTotaisCarbono    =+ Math.pow((qtdeCarbonoObs - qtdeCarbonoObsMed), 2);
                  }
               if (qtdArvores>0){
                     r2Carbono = somaQuadradoRegressaoCarbono / somaQuadradoTotaisCarbono;
                        if(qtdArvores>3) {
                            r2AjustCarbono = 1 - (1-r2Carbono) * ((qtdArvores - 1) / (qtdArvores - 2 - 1));
                        } else {
                            r2AjustCarbono = 1 - (1-r2Carbono) * ((qtdArvores - 1) / (qtdArvores - 1));
                            syxCarbono     = Math.sqrt(somaQuadradoResiduoCarbono / (qtdArvores));
                        }
                        //Syx% = (Syx/volumeObsMedio)*100
                        syxPercCarbono = (syxCarbono / qtdeCarbonoObsMed) * 100;
                        iaCarbono = 1 - (somaQuadradoResiduoCarbono/somaQuadradoTotaisCarbono);
                        
                        estatistica.numLocal=idLocal;
                        estatistica.r2=r2Carbono;
                        estatistica.r2Ajust=r2AjustCarbono;
                        estatistica.ia=iaCarbono;
                        estatistica.syx=syxCarbono;
                        estatistica.syxPerc = syxPercCarbono;
                     return estatistica;
               }
               
              break;
          case 3:
              for(ArvoreAjuste arvore : ArvoreAjuste.findArvoreAjuste(idLocal, equacao.id)){
                    somaVolumeObs =+ arvore.qtd_volume_obs;
                    
                    parser = new org.nfunk.jep.JEP();
                    parser.addStandardFunctions();
                    parser.addStandardConstants();
                    for(EquacaoVariavel equacaoVariavel : equacao.equacao_variavel){
                        for(ArvoreAjusteVariavel arvoreAjusteVariavel : arvore.arvore_ajuste_variavel)
                            if(equacaoVariavel.variavel.id == arvoreAjusteVariavel.variavel.id){
                                parser.addVariable(equacaoVariavel.variavel.sigla, arvoreAjusteVariavel.valor);
                            }
                    }
                        parser.parseExpression(equacao.expressao);
                        qtdeVolumeEst = parser.getValue();
                        arvore.qtd_volume_est = qtdeVolumeEst;
                        somaVolumeEst = somaVolumeEst + qtdeVolumeEst;
                        arvore.update();
                
                    }
                    local = Local.find.byId(idLocal);
                  if (qtdArvores>0) {
                    qtdeVolumeObsMed = somaVolumeObs / local.arvore_ajuste.size();
                  } else {
                    qtdeVolumeObsMed = 0.0;
                  }
                  for(ArvoreAjuste arvore : ArvoreAjuste.findArvoreAjuste(idLocal, equacao.id)){
                      qtdeVolumeObs = arvore.qtd_volume_obs;
                      qtdeVolumeEst = arvore.qtd_volume_est;
                      somaQuadradoResiduoVolume   =+ Math.pow((qtdeVolumeObs - qtdeVolumeEst), 2);
                      somaQuadradoRegressaoVolume =+ Math.pow((qtdeVolumeEst - qtdeVolumeObsMed), 2);
                      somaQuadradoTotaisVolume    =+ Math.pow((qtdeVolumeObs - qtdeVolumeObsMed), 2);
                  }
               if (qtdArvores>0){
                     r2Volume = somaQuadradoRegressaoVolume / somaQuadradoTotaisVolume;
                        if(qtdArvores>3) {
                            r2AjustVolume = 1 - (1-r2Volume) * ((qtdArvores - 1) / (qtdArvores - 2 - 1));
                        } else {
                            r2AjustVolume = 1 - (1-r2Volume) * ((qtdArvores - 1) / (qtdArvores - 1));
                            syxVolume     = Math.sqrt(somaQuadradoResiduoVolume / (qtdArvores));
                        }
                        //Syx% = (Syx/volumeObsMedio)*100
                        syxPercVolume = (syxVolume / qtdeVolumeObsMed) * 100;
                        iaVolume = 1 - (somaQuadradoResiduoVolume/somaQuadradoTotaisVolume);
                        
                        estatistica.numLocal=idLocal;
                        estatistica.r2=r2Volume;
                        estatistica.r2Ajust=r2AjustVolume;
                        estatistica.ia=iaVolume;
                        estatistica.syx=syxVolume;
                        estatistica.syxPerc = syxPercVolume;
                     
               }
               break;
        }
        return estatistica;
    }
}