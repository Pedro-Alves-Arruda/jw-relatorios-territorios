package com.jw.relatorios_territorios.Producers;


import com.jw.relatorios_territorios.DTO.PublicadorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginProducers {

    @Autowired
    private KafkaTemplate<String, String> template;

    @Autowired
    private KafkaTemplate<String, PublicadorDTO> templateRedefinicao;

    private static final Logger log = LoggerFactory.getLogger(LoginProducers.class);

    public String enviarMensagemTopicoRedefinicaoSenha(String email){
        try{
            log.info("Enviando mensagem para topico redefinicao-senha");
            template.send("redefinicao-senha", email);
            return "Link enviado com sucesso";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String enviarMensagemAtualizarSenha(PublicadorDTO publicadorDTO){
        try{
            log.info("recebendo mensagem para redefinir a senha");
            templateRedefinicao.send("atualizar-senha", publicadorDTO);
            return "Mensagem para redefinição de senha enviada com sucesso";

        } catch (KafkaProducerException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String enviarMensagemSolicitacaoUsuarioNovo(PublicadorDTO publicadorDTO){
        try{
            log.info("Enviando mensagem de solicitação de usuario novo");
            templateRedefinicao.send("solicitar-usuario-novo", publicadorDTO);
            return "mensagem de solicitação de usuario novo enviada com sucesso";
        }catch (KafkaProducerException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
