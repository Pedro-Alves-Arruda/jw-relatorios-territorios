package com.jw.relatorios_territorios.Consumers;

import com.jw.relatorios_territorios.DTO.PublicadorDTO;
import com.jw.relatorios_territorios.Services.LoginServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;


@Service
public class LoginConsumers {

    @Autowired
    private LoginServices loginServices;

    private static final Logger log = LoggerFactory.getLogger(LoginConsumers.class);

    @KafkaListener(topics = "redefinicao-senha", groupId = "relatorios_territorios")
    public void receberMensagemRedefinicaoSenha(String email) throws ExecutionException, InterruptedException {
        log.info("Recebendo mensagem de redefinição de senha");
        this.loginServices.enviarLinkRedefinicaoSenha(email);
    }

    @KafkaListener(topics = "atualizar-senha", groupId = "relatorios_territorios")
    public void receberMensagemAtualizarSenha(PublicadorDTO publicadorDTO) throws ExecutionException, InterruptedException {
        log.info("Recebendo mensagem de atualizar senha");
        this.loginServices.atualizarSenha(publicadorDTO);
    }

    @KafkaListener(topics = "solicitar-usuario-novo", groupId = "relatorios_territorios")
    public void receberMensagemSolicitacaoUsuarioNovo(PublicadorDTO publicadorDTO){
        log.info("Recebendo mensagem de solicitar usuario novo");
        this.loginServices.enviarSolicitacaoUsuarioNovo(publicadorDTO);
    }

}
