package com.jw.relatorios_territorios.WebSockets;

import com.jw.relatorios_territorios.Services.NotificacaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RelatorioSockets {

    @Autowired
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotificacaoServices notificacaoServices;

    public RelatorioSockets(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public boolean enviarNotificacaoRelatorio(){
        try{
            simpMessagingTemplate.convertAndSend("/topic/notificacoes/relatorios", "Hoje é dia primeiro, não se esqueça de enviar seu relatorio, clique aqui caso queira enviar");
            this.notificacaoServices.salvarNotificacaoRelatorioGenerica();
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
