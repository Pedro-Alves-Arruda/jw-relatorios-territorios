package com.jw.relatorios_territorios.WebSockets;

import com.jw.relatorios_territorios.DTO.NotificacaoDTO;
import com.jw.relatorios_territorios.Services.NotificacaoServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(RelatorioSockets.class);

    public RelatorioSockets(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public boolean enviarNotificacaoRelatorio(){
        try{
            NotificacaoDTO notificacaoDTO = new NotificacaoDTO(
                    null,
                    "/topic/notificacoes/relatorios",
                    "Hoje é dia 09/09/2025, não se esqueça de enviar seu relatorio, clique aqui caso queira enviar",
                    null,
                    LocalDateTime.now(),
                    false
            );
            simpMessagingTemplate.convertAndSend("/topic/notificacoes/relatorios", notificacaoDTO);
            this.notificacaoServices.salvarNotificacaoRelatorioGenerica(notificacaoDTO);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void enviarNotificacaoRelatorioResponsavel(Object email, NotificacaoDTO notificacaoDTO){
        try{
            log.info("Enviando notificação para topic /topic/notificacoes/{}", email.toString());
            this.simpMessagingTemplate.convertAndSend("/topic/notificacoes/"+ email, notificacaoDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
