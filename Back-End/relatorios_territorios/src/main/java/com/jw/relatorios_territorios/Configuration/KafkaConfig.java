package com.jw.relatorios_territorios.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    //Publicador
    public NewTopic publicadorSalvar(){
        return TopicBuilder.name("publicador-salvar").build();
    }



    //Revisita
    public NewTopic revisitaSalvar() { return TopicBuilder.name("revisita-salvar").build(); }
 }
