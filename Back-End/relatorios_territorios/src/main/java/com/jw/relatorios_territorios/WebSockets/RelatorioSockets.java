package com.jw.relatorios_territorios.WebSockets;

import com.jw.relatorios_territorios.DTO.NotificacaoDTO;
import com.jw.relatorios_territorios.Services.NotificacaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
            NotificacaoDTO notificacaoDTO = new NotificacaoDTO(
                    "/topic/notificacoes/relatorios",
                    "Hoje é dia 09/09/2025, não se esqueça de enviar seu relatorio, clique aqui caso queira enviar",
                    null,
                    LocalDateTime.now()
            );
            simpMessagingTemplate.convertAndSend("/topic/notificacoes/relatorios", notificacaoDTO);
            this.notificacaoServices.salvarNotificacaoRelatorioGenerica(notificacaoDTO);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
