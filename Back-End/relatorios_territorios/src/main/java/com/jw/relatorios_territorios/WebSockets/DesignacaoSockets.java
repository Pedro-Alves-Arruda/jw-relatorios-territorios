package com.jw.relatorios_territorios.WebSockets;


import com.jw.relatorios_territorios.DTO.NotificacaoDTO;
import com.jw.relatorios_territorios.Models.Notificacao;
import com.jw.relatorios_territorios.Repository.NotificacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DesignacaoSockets {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    private static final Logger log = LoggerFactory.getLogger(DesignacaoSockets.class);

    public DesignacaoSockets(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public boolean enviarNotificacaoDesignacao(Notificacao notificacao, String email){
        try{
            NotificacaoDTO notificacaoDTO = new NotificacaoDTO(
                    null,
                    "/topic/notificacoes/"+email,
                    notificacao.getMessage(),
                    null,
                    LocalDateTime.now(),
                    false
            );
            simpMessagingTemplate.convertAndSend("/topic/notificacoes/"+email, notificacaoDTO);
            notificacaoRepository.save(notificacao);
            log.info("Notificação de designação enviada para usuario em questão");
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
