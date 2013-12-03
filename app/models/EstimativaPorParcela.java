package models;

import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.distribution.TDistribution;


public class EstimativaPorParcela {
    
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
    int tamanhoAmostra = 0;
    DescriptiveStatistics estatistica = new DescriptiveStatistics();                
    DescriptiveStatistics estatisticaBiomassa = new DescriptiveStatistics();                
    DescriptiveStatistics estatisticaCarbono = new DescriptiveStatistics();                
    DescriptiveStatistics estatisticaVolume = new DescriptiveStatistics();                
    
    public void calculaEstimativaPorParcela(Local local){
        LocalDetalheBiomassa localDetalheBiomassa = new LocalDetalheBiomassa();
        LocalDetalheCarbono localDetalheCarbono = new LocalDetalheCarbono();
        LocalDetalheVolume  localDetalheVolume = new LocalDetalheVolume();   
        tamanhoAmostra = local.parcelas.size();        
        qtdeParcelasLocal = local.area_total / local.area_parcela;
        umMenosF = 1 - (tamanhoAmostra / qtdeParcelasLocal);        
        //Constante t. Com 5% (foi usado 0,025 para ser bicaudal) e n-1 fica:
        t = getT(0.025,tamanhoAmostra - 1);

        //BIOMASSA        
        for(Parcela parcela : local.parcelas) {
            estatisticaBiomassa.addValue(parcela.biomassa);            
        }
        estatistica = estatisticaBiomassa.copy();
        calculaEstatisticas();
        
        double qtdeBiomassaMin = mediaLocal - qtdeParcelasLocal * erroAbsoluto;
        double qtdeBiomassaMax = mediaLocal + qtdeParcelasLocal * erroAbsoluto;
        double qtdeBiomassaMed = mediaLocal;
        
        localDetalheBiomassa.qtde_biomassa_min = qtdeBiomassaMin;
        localDetalheBiomassa.qtde_biomassa_max=qtdeBiomassaMax;        
        localDetalheBiomassa.qtde_biomassa_med=qtdeBiomassaMed;
        localDetalheBiomassa.media_parcela=mediaParcela;
        localDetalheBiomassa.variancia=variancia;
        localDetalheBiomassa.desvio_padrao=desvioPadrao;
        localDetalheBiomassa.variancia_media=varianciaMedia;
        localDetalheBiomassa.erro_padrao=erroPadrao;
        localDetalheBiomassa.coeficiente_variacao=coeficienteVariacao;                
        localDetalheBiomassa.erro_absoluto=erroAbsoluto;        
        localDetalheBiomassa.erro_relativo=erroRelativo;                
        localDetalheBiomassa.intervalo_confianca_min_parcela=intervaloConfiancaMinParcela;
        localDetalheBiomassa.intervalo_confianca_max_parcela=intervaloConfiancaMaxParcela;              
        
        //CARBONO
        for(Parcela parcela : local.parcelas) {
            estatisticaCarbono.addValue(parcela.carbono);            
        }
        estatistica = estatisticaCarbono.copy();
        calculaEstatisticas();
        
        double qtdeCarbonoMin = mediaLocal - qtdeParcelasLocal * erroAbsoluto;
        double qtdeCarbonoMax = mediaLocal + qtdeParcelasLocal * erroAbsoluto;
        double qtdeCarbonoMed = mediaLocal;
        
        localDetalheCarbono.qtde_carbono_min = qtdeCarbonoMin;
        localDetalheCarbono.qtde_carbono_max=qtdeCarbonoMax;        
        localDetalheCarbono.qtde_carbono_med=qtdeCarbonoMed;
        localDetalheCarbono.media_parcela=mediaParcela;
        localDetalheCarbono.variancia=variancia;
        localDetalheCarbono.desvio_padrao=desvioPadrao;
        localDetalheCarbono.variancia_media=varianciaMedia;
        localDetalheCarbono.erro_padrao=erroPadrao;
        localDetalheCarbono.coeficiente_variacao=coeficienteVariacao;                
        localDetalheCarbono.erro_absoluto=erroAbsoluto;        
        localDetalheCarbono.erro_relativo=erroRelativo;                
        localDetalheCarbono.intervalo_confianca_min_parcela=intervaloConfiancaMinParcela;
        localDetalheCarbono.intervalo_confianca_max_parcela=intervaloConfiancaMaxParcela;        
 
        //VOLUME
        estatistica.clear();        
        for(Parcela parcela : local.parcelas) {
            estatisticaVolume.addValue(parcela.volume);            
        }
        
        calculaEstatisticas();
        
        double qtdeVolumeMin = mediaLocal - qtdeParcelasLocal * erroAbsoluto;
        double qtdeVolumeMax = mediaLocal + qtdeParcelasLocal * erroAbsoluto;
        double qtdeVolumeMed = mediaLocal;
        
        
        
        localDetalheVolume.qtde_volume_min = qtdeVolumeMin;
        localDetalheVolume.qtde_volume_max=qtdeVolumeMax;        
        localDetalheVolume.qtde_volume_med=qtdeVolumeMed;
        localDetalheVolume.media_parcela=mediaParcela;
        localDetalheVolume.variancia=variancia;
        localDetalheVolume.desvio_padrao=desvioPadrao;
        localDetalheVolume.variancia_media=varianciaMedia;
        localDetalheVolume.erro_padrao=erroPadrao;
        localDetalheVolume.coeficiente_variacao=coeficienteVariacao;                
        localDetalheVolume.erro_absoluto=erroAbsoluto;        
        localDetalheVolume.erro_relativo=erroRelativo;                
        localDetalheVolume.intervalo_confianca_min_parcela=intervaloConfiancaMinParcela;
        localDetalheVolume.intervalo_confianca_max_parcela=intervaloConfiancaMaxParcela;        
        
        
        local.qtde_biomassa = qtdeBiomassaMed;
        local.qtde_carbono = qtdeCarbonoMed;
        local.qtde_volume = qtdeVolumeMed;
        local.local_detalhe_biomassa = localDetalheBiomassa;
        local.local_detalhe_carbono = localDetalheCarbono;
        local.local_detalhe_volume = localDetalheVolume;
        local.update();
            
    }
    
    public static double getT(double nivelDeProbabilidade, double grausDeLiberdade){
       TDistribution tDist = new TDistribution(grausDeLiberdade);
       //return Math.abs(tDist.inverseCumulativeProbability(nivelDeProbabilidade));
       
       return Math.abs(tDist.inverseCumulativeProbability(nivelDeProbabilidade));
    }
    public void calculaEstatisticas(){
        mediaParcela                 = estatistica.getMean();
        variancia                    = estatistica.getVariance();
        desvioPadrao                 = estatistica.getStandardDeviation();
        erroPadrao                   = (desvioPadrao / Math.sqrt(tamanhoAmostra) * Math.sqrt(umMenosF));
        coeficienteVariacao          = (desvioPadrao / mediaParcela);
        varianciaMedia               = (variancia / tamanhoAmostra) * ((qtdeParcelasLocal-tamanhoAmostra)/qtdeParcelasLocal);
        erroAbsoluto                 = erroPadrao * t;
        erroRelativo                 = (erroAbsoluto / mediaParcela) * 100;
        intervaloConfiancaMinParcela = mediaParcela - erroAbsoluto;
        intervaloConfiancaMaxParcela = mediaParcela + erroAbsoluto;
        mediaLocal                   = qtdeParcelasLocal * mediaParcela;
    }
    
}
