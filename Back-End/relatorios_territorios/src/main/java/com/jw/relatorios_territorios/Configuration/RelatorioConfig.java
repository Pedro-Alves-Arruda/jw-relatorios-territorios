package com.jw.relatorios_territorios.Configuration;


import com.jw.relatorios_territorios.Services.RelatorioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RelatorioConfig {

    @Autowired
    private RelatorioServices relatorioServices;

    @Scheduled(cron = "0 0 12 1 * * ?")
    public void verificarEnvioRelatorio(){
        try{
            this.relatorioServices.verificarRelatorioParaEnvio();
        }catch (KafkaProducerException e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
