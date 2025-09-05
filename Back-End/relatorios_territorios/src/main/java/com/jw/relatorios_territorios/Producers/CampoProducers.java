package com.jw.relatorios_territorios.Producers;

import com.jw.relatorios_territorios.DTO.ServicoCampoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CampoProducers {

    @Autowired
    private KafkaTemplate<String, ServicoCampoDTO> template;

    private static final Logger log = LoggerFactory.getLogger(CampoProducers.class);

    public String enviarMensagemTopicoServicoCampo(ServicoCampoDTO servicoCampoDTO){
        try{
            template.send("servico-campo", servicoCampoDTO);
            log.info("enviando mensagem para topico servico-campo");
            return "Mensagem enviada com sucesso para topico servico-campo";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
