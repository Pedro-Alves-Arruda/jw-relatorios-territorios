package com.jw.relatorios_territorios.Configuration;


import com.jw.relatorios_territorios.Services.RelatorioServices;
import com.jw.relatorios_territorios.WebSockets.RelatorioSockets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RelatorioConfig {

    @Autowired
    private RelatorioServices relatorioServices;

    @Autowired
    private RelatorioSockets relatorioSockets;

    private static final Logger log = LoggerFactory.getLogger(RelatorioConfig.class);

    @Scheduled(cron = "0 40 17 11 * ?")
    public void verificarEnvioRelatorio(){
        try{
            this.relatorioServices.verificarRelatorioParaEnvio();
        }catch (KafkaProducerException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Scheduled(cron = "0 36 10 9 * ?")
    public void EnvioNotificacaoRelatorio(){
        try{
            this.relatorioSockets.enviarNotificacaoRelatorio();
            log.info("Notificação generica de relatorio enviada para todos os usuarios");
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
