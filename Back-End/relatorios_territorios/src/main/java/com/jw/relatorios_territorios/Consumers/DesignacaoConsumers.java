package com.jw.relatorios_territorios.Consumers;


import com.jw.relatorios_territorios.DTO.DesignacaoDTO;
import com.jw.relatorios_territorios.Services.DesignacaoServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DesignacaoConsumers {

    @Autowired
    private DesignacaoServices designacaoServices;

    private static  final Logger log = LoggerFactory.getLogger(DesignacaoConsumers.class);

    @KafkaListener(topics = "designacao-salvar", groupId = "relatorios_territorios")
    public void receberMensagemSalvarDesignacao(DesignacaoDTO designacaoDTO){
        try{
            log.info("Recebendo mensagem topico designacao salvar");
            designacaoServices.nova(designacaoDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
