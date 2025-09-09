package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.Models.Notificacao;
import com.jw.relatorios_territorios.Repository.NotificacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificacaoServices {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    private static final Logger log = LoggerFactory.getLogger(NotificacaoServices.class);

    public void salvarNotificacaoRelatorioGenerica(){
        //montando entidade para ser salva
        Notificacao notificacao = new Notificacao();
        notificacao.setTopic("/topic/notificacoes/relatorios");
        notificacao.setMessage("Hoje é dia primeiro, não se esqueça de enviar seu relatorio, clique aqui caso queira enviar");
        notificacao.setCreatedAt(LocalDateTime.now());

        try{
            log.info("Enviando notificação para ser salva no banco de dados");
            this.notificacaoRepository.save(notificacao);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
