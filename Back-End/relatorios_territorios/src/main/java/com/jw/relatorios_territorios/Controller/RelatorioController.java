package com.jw.relatorios_territorios.Controller;

import com.jw.relatorios_territorios.DTO.RelatorioDTO;
import com.jw.relatorios_territorios.Producers.RelatorioProducers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Relatorio")
public class RelatorioController {


    @Autowired
    private RelatorioProducers relatorioProducers;

    @PostMapping
    public ResponseEntity<String> novo(@RequestBody RelatorioDTO relatorioDTO){
        try{
            relatorioProducers.enviarMensagemSalvar(relatorioDTO);
            return ResponseEntity.ok().body("Relatorio inserido com sucesso");
        }catch (KafkaProducerException e){
            return ResponseEntity.internalServerError().body(e.getMessage() + e.getCause());
        }
    }
}
