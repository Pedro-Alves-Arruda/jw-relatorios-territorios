package com.jw.relatorios_territorios.Producers;

import com.jw.relatorios_territorios.DTO.RevisitaDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevisitaProducers {

    @Autowired
    private KafkaTemplate<String, RevisitaDTO> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(RevisitaProducers.class);

    public void sendMessage(RevisitaDTO revisitaDTO){
        try{
            log.info("Enviando mensagem para topico revisita-salvar, mensagem: {}", revisitaDTO);
            kafkaTemplate.send("revisita-salvar",revisitaDTO);
        }catch (KafkaProducerException ex){
            log.error("Erro ao produzir topico. Erro: {}", ex.getMessage());
        }


    }

}
