package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.DTO.NotificacaoDTO;
import com.jw.relatorios_territorios.DTO.RelatorioDTO;
import com.jw.relatorios_territorios.Models.Notificacao;
import com.jw.relatorios_territorios.Models.Publicador;
import com.jw.relatorios_territorios.Models.Relatorio;
import com.jw.relatorios_territorios.Repository.NotificacaoRepository;
import com.jw.relatorios_territorios.Repository.PublicadorRepository;
import com.jw.relatorios_territorios.Repository.RelatorioRepository;
import com.jw.relatorios_territorios.Repository.RevisitaRepository;
import com.jw.relatorios_territorios.WebSockets.RelatorioSockets;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class RelatorioServices {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenServices tokenServices;

    @Autowired
    private PublicadorRepository publicadorRepository;

    @Autowired
    private RevisitaRepository revisitaRepository;

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RelatorioSockets relatorioSockets;

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    private String mesAtual = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));


    public void salvar(RelatorioDTO relatorioDTO){

        //pegar usuario em questão
        Publicador publicador = getPublicador();

        //pegar numero de revisitas
        Integer countRevisitas = getCountRevisitas(publicador.getId());

        //montar objeto para ser salvo
        Relatorio relatorio = new Relatorio();
        relatorio.setNome(publicador.getNome());
        relatorio.setHoras(relatorioDTO.horas());
        relatorio.setIdPublicador(publicador.getId());
        relatorio.setRevisitas(countRevisitas);
        relatorio.setGrupoCampo(publicador.getGrupoCampo().getId());

        try{
            this.relatorioRepository.save(relatorio);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void verificarRelatorioParaEnvio(){
        List<Object[]> listRelatorios = this.relatorioRepository.findByRelatoriosForSend();
        if(!listRelatorios.isEmpty()){
            listRelatorios.stream()
                .map( a -> {
                    this.emailService.enviarEmailRelatorioPublicador(a);
                    Notificacao notificacao = this.notificacaoRepository.save(getNotificacao(a));
                    this.relatorioSockets.enviarNotificacaoRelatorioResponsavel(a[5], getNotificacaoDTO(notificacao));
                    return true;
                })
                .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    private Publicador getPublicador(){
        String email = this.tokenServices.tokenToEmail();
        try{
            return this.publicadorRepository.findByEmail(email).get();
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    private Integer getCountRevisitas(UUID idPublicador){
        try{
            return this.revisitaRepository.findByCountRevisita(idPublicador);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private Notificacao getNotificacao(Object[] relatorio){
        Notificacao notificacao = new Notificacao();
        notificacao.setMessage(relatorio[0]+": Relatorio enviado para email. Relatorio referente ao mês de"+ this.mesAtual+". Trabalhou no campo? sim");
        notificacao.setTopic("/topic/notificacoes/"+relatorio[5]);
        notificacao.setLida(false);
        notificacao.setCreatedAt(LocalDateTime.now());
        notificacao.setIdPublicador(UUID.fromString(relatorio[6].toString()));
        return notificacao;
    }
    private NotificacaoDTO getNotificacaoDTO(Notificacao notificacao){
       return new NotificacaoDTO(
                notificacao.getId(),
                notificacao.getTopic(),
                notificacao.getMessage(),
                notificacao.getIdPublicador(),
                notificacao.getCreatedAt(),
                notificacao.isLida()
       );
    }
}
