package com.jw.relatorios_territorios.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public NewTopic publicadorSalvar(){
        return TopicBuilder.name("publicador-salvar").build();
    }

}
