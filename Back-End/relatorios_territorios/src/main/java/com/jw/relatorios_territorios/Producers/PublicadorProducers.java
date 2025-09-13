package com.jw.relatorios_territorios.Producers;

import com.jw.relatorios_territorios.Consumers.PublicadorConsumers;
import com.jw.relatorios_territorios.DTO.FotoPerfilDTO;
import com.jw.relatorios_territorios.DTO.PublicadorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicadorProducers {

    @Autowired
    private KafkaTemplate<String, PublicadorDTO> template;

    @Autowired KafkaTemplate<String, FotoPerfilDTO> templateString;

    private static final Logger log = LoggerFactory.getLogger(PublicadorProducers.class);

    public void enviarMensagemSalvar(PublicadorDTO publicadorDTO){
        log.info("Enviado mensagem para topico publicador-salvar");
        template.send("publicador-salvar", publicadorDTO);
    }

    public void enviarMensagemSalvarFotoPerfil(FotoPerfilDTO fotoPerfilDTO){
        try{
            log.info("enviando foto de perfil para ser salva no S3");
            templateString.send("publicador-salvar-foto-perfil", fotoPerfilDTO);
        }catch (KafkaProducerException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

}
