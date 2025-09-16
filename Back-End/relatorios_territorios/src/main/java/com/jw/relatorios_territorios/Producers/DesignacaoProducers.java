package com.jw.relatorios_territorios.Producers;


import com.jw.relatorios_territorios.DTO.DesignacaoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DesignacaoProducers {

    private KafkaTemplate<String, DesignacaoDTO> template;

    private static final Logger log = LoggerFactory.getLogger(DesignacaoProducers.class);

    public void enviarMensagemSalvarNovaDesignacao(DesignacaoDTO designacaoDTO) throws Exception {
        try{
            log.info("Enviando mensagem para topico de salvar designacao");
            template.send("designacao-salvar", designacaoDTO);
        }catch (KafkaProducerException e){
            throw new Exception(e.getMessage());
        }
    }

}
