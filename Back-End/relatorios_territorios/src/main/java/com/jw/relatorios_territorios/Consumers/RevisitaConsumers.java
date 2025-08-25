package com.jw.relatorios_territorios.Consumers;


import com.jw.relatorios_territorios.DTO.RevisitaDTO;
import com.jw.relatorios_territorios.Producers.RevisitaProducers;
import com.jw.relatorios_territorios.Services.RevisitaServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RevisitaConsumers {

    @Autowired
    private RevisitaServices revisitaServices;
    private static final Logger log = LoggerFactory.getLogger(RevisitaConsumers.class);

    @KafkaListener(topics = "revisita-salvar", groupId = "relatorios_territorios")
    public void receberMensagemSalvar(RevisitaDTO revisitaDTO){
        log.info("Enviando objeto para ser salvo.");
        revisitaServices.salvar(revisitaDTO);
    }
}
