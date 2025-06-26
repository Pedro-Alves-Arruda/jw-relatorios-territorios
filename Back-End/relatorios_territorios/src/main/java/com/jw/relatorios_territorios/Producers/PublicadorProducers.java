package com.jw.relatorios_territorios.Producers;

import com.jw.relatorios_territorios.Consumers.PublicadorConsumers;
import com.jw.relatorios_territorios.DTO.PublicadorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicadorProducers {

    @Autowired
    private KafkaTemplate<String, PublicadorDTO> template;

    private static final Logger log = LoggerFactory.getLogger(PublicadorProducers.class);

    public void enviarMensagemSalvar(PublicadorDTO publicadorDTO){
        log.info("Enviado mensagem para topico publicador-salvar");
        template.send("publicador-salvar", publicadorDTO);
    }

}
