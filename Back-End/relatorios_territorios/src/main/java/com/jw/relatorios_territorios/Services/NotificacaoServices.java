package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.DTO.NotificacaoDTO;
import com.jw.relatorios_territorios.Models.Notificacao;
import com.jw.relatorios_territorios.Repository.NotificacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificacaoServices {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    private static final Logger log = LoggerFactory.getLogger(NotificacaoServices.class);

    public void salvarNotificacaoRelatorioGenerica(NotificacaoDTO notificacaoDTO){
        //montando entidade para ser salva
        Notificacao notificacao = new Notificacao();
        notificacao.setTopic(notificacaoDTO.topic());
        notificacao.setMessage(notificacaoDTO.message());
        notificacao.setCreatedAt(notificacaoDTO.createdAt());

        try{
            log.info("Enviando notificação para ser salva no banco de dados");
            this.notificacaoRepository.save(notificacao);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    public List<NotificacaoDTO> buscarNotificacoesComuns(){
        try{
            //buscar as notificações em coomum
            List<Notificacao> notificacoes = this.notificacaoRepository.findAllCommon();

            //convertendo para DTO
            return convertModelToDTO(notificacoes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<NotificacaoDTO> convertModelToDTO(List<Notificacao> notificacoes){
        List<NotificacaoDTO> notificacoesDTO = new ArrayList<>();
        for(Notificacao notificacao : notificacoes){
            NotificacaoDTO notificacaoDTO = new NotificacaoDTO(
                    notificacao.getTopic(),
                    notificacao.getMessage(),
                    null,
                    notificacao.getCreatedAt()
            );

            notificacoesDTO.add(notificacaoDTO);

        }
        return notificacoesDTO;
    }
}
