package com.jw.relatorios_territorios.Services;

import com.jw.relatorios_territorios.DTO.DesignacaoDTO;
import com.jw.relatorios_territorios.DTO.PublicacoesDeixadasDTO;
import com.jw.relatorios_territorios.Models.Designacao;
import com.jw.relatorios_territorios.Models.Notificacao;
import com.jw.relatorios_territorios.Models.Publicador;
import com.jw.relatorios_territorios.Repository.DesignacaoRepository;
import com.jw.relatorios_territorios.Repository.PublicadorRepository;
import com.jw.relatorios_territorios.WebSockets.DesignacaoSockets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DesignacaoServices {

    @Autowired
    private DesignacaoRepository designacaoRepository;

    @Autowired
    private PublicadorRepository publicadorRepository;

    @Autowired
    private DesignacaoSockets designacaoSockets;

    public void nova(DesignacaoDTO designacaoDTO){

        //montar obj para ser salvo
        var designacao = new Designacao();
        designacao.setDesignacao(designacaoDTO.designacao());
        designacao.setDia(designacaoDTO.dia());
        designacao.setIdPublicador(designacaoDTO.idPublicador());
        designacao.setCreatedAt(LocalDateTime.now());

        try{
            //salvar obj
            designacaoRepository.save(designacao);

            //enviar Notificacao de designacao para usuario em questão

                //pegar email do usuario que a notificacao sera enviada
                Publicador publicador = publicadorRepository.findById(designacaoDTO.idPublicador()).get();
                //montar a notificacao que sera enviada
                Notificacao notificacao = new Notificacao();
                notificacao.setMessage("Segue designação abaixo\nDesignação:"+designacaoDTO.designacao()+"\nDia:"+designacaoDTO.dia());
                designacaoSockets.enviarNotificacaoDesignacao(notificacao, publicador.getEmail());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    public List<DesignacaoDTO> listar(String email){
        try{
            Publicador publicador = publicadorRepository.findByEmail(email).get();
            List<Designacao> designacoes = designacaoRepository.findAllByIdPublicador(publicador.getId());
            return designacoes
                    .stream()
                    .map(e -> new DesignacaoDTO(e.getDesignacao(), null , e.getDia(), null))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
