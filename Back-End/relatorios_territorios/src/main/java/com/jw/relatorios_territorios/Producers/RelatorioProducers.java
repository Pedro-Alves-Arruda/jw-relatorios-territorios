package com.jw.relatorios_territorios.Producers;

import com.jw.relatorios_territorios.DTO.RelatorioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RelatorioProducers {

    @Autowired
    private KafkaTemplate<String, RelatorioDTO> template;
    private static final Logger log = LoggerFactory.getLogger(RelatorioProducers.class);

    public void enviarMensagemSalvar(RelatorioDTO relatorioDTO) {
        try{
            log.info("Enviando objeto para ser salvo, topico relatorio-salvar");
            this.template.send("relatorio-salvar", relatorioDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
