package com.jw.relatorios_territorios.WebSockets;


import com.jw.relatorios_territorios.Models.Notificacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class LoginSockets {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final Logger log = LoggerFactory.getLogger(LoginSockets.class);

    public LoginSockets(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public boolean enviarNotificacaoSolicitacaoUsuarioNovo(Notificacao notificacao){
        try{
            this.simpMessagingTemplate.convertAndSend(notificacao.getTopic(), notificacao);
            log.info("Notificação de criação de novo usuario enviada com sucesso");
            return true;
        }catch (MessagingException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }


}
