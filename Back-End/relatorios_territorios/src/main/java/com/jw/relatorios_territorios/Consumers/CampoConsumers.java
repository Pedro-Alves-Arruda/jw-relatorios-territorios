package com.jw.relatorios_territorios.Consumers;

import com.jw.relatorios_territorios.DTO.ServicoCampoDTO;
import com.jw.relatorios_territorios.Producers.CampoProducers;
import com.jw.relatorios_territorios.Services.CampoServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CampoConsumers {

    private static final Logger log = LoggerFactory.getLogger(CampoProducers.class);

    @Autowired
    private CampoServices campoServices;

    @KafkaListener(topics = "servico-campo", groupId = "relatorios_territorios")
    public void receberMensagemTopicoServicoCampo(ServicoCampoDTO servicoCampoDTO){
        try{
            log.info("recebendo mensagem topico servico-campo");
            this.campoServices.salvarServicoCampo(servicoCampoDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
