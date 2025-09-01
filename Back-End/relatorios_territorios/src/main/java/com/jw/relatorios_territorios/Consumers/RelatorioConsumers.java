package com.jw.relatorios_territorios.Consumers;

import com.jw.relatorios_territorios.DTO.RelatorioDTO;
import com.jw.relatorios_territorios.Services.RelatorioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RelatorioConsumers {

    @Autowired
    private RelatorioServices relatorioServices;


    @KafkaListener(topics = "relatorio-salvar", groupId = "relatorios_territorios")
    public void receberMensagemSalvar(RelatorioDTO relatorioDTO){
        try{
            this.relatorioServices.salvar(relatorioDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
