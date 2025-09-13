package com.jw.relatorios_territorios.Consumers;


import com.jw.relatorios_territorios.DTO.FotoPerfilDTO;
import com.jw.relatorios_territorios.DTO.PublicadorDTO;
import com.jw.relatorios_territorios.Services.PublicadorServices;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PublicadorConsumers {

    @Autowired
    private PublicadorServices services;

    private static final Logger log = LoggerFactory.getLogger(PublicadorConsumers.class);

    @KafkaListener(topics = "publicador-salvar", groupId = "relatorios_territorios")
    public void receberMensagemSalvar(PublicadorDTO publicadorDTO){
        log.info("Recebendo mensagem de publicador-salvar, mensagem: {}", publicadorDTO);
        services.salvar(publicadorDTO);
    }

    @KafkaListener(topics = "publicador-salvar-foto-perfil", groupId = "relatorios_territorios")
    public void receberMensagemSalvarFotoPerfil(FotoPerfilDTO fotoPerfil){
        log.info("recebendo mensagem topico publicador-salvar-foto-perfil");
        services.salvarFotoPerfil(fotoPerfil);
    }
}
