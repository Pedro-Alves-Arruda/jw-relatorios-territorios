package com.jw.relatorios_territorios.Services;


import org.apache.kafka.clients.admin.ListOffsetsResult;
import org.apache.kafka.clients.admin.RecordsToDelete;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.*;


@Service
public class KafkaServices {

    @Autowired
    private AdminClient adminKafka1;
    @Autowired
    private AdminClient adminKafka2;
    @Autowired
    private Properties propskafka1;
    @Autowired
    private Properties propskafka2;
    private static final Logger log = LoggerFactory.getLogger(KafkaServices.class);


    public void kafkaServices(){
        this.propskafka1.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9096");
        this.propskafka2.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9097");
        this.adminKafka1 = AdminClient.create(this.propskafka1);
        this.adminKafka2 = AdminClient.create(this.propskafka2);
    }

    public boolean deletarTodasMensagens(String topico){
        //implementação
        return true;
    }

    public boolean deletarUltimaMensagem(String topico) throws ExecutionException, InterruptedException {
        TopicPartition partition  = new TopicPartition(topico, 0);
        try{
            if(this.adminKafka1.listOffsets(Collections.singletonMap(partition, OffsetSpec.latest())) != null){
                Map<TopicPartition, ListOffsetsResult.ListOffsetsResultInfo> endOffSetKafka1 =
                        this.adminKafka1.listOffsets(Collections.singletonMap(partition, OffsetSpec.latest())).all().get();
                long ultimoOfssetKafka1 = endOffSetKafka1.get(partition).offset();
                if(ultimoOfssetKafka1 > 0)
                    return deletarMensagem(partition, ultimoOfssetKafka1, "kafka1");
            }

            if(this.adminKafka2.listOffsets(Collections.singletonMap(partition, OffsetSpec.latest())) != null){
                Map<TopicPartition, ListOffsetsResult.ListOffsetsResultInfo> endOffSetKafka2 =
                        this.adminKafka2.listOffsets(Collections.singletonMap(partition, OffsetSpec.latest())).all().get();
                long ultimoOfssetKafka2 = endOffSetKafka2.get(partition).offset();
                if(ultimoOfssetKafka2 > 0)
                    return deletarMensagem(partition, ultimoOfssetKafka2, "kafka2");
            }
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Boolean deletarMensagem(TopicPartition partition, long ultimoOfsset, String kafka) throws ExecutionException, InterruptedException {
        Map<TopicPartition, RecordsToDelete> deleteMap = new HashMap<>();
        deleteMap.put(partition, RecordsToDelete.beforeOffset(ultimoOfsset -1));
        
        try {
            DeleteRecordsResult result;
            if(kafka.equals("kafka1")){
                result = this.adminKafka1.deleteRecords(deleteMap);
                result.all().get();
            }else if(kafka.equals("kafka2")){
                result = this.adminKafka2.deleteRecords(deleteMap);
                result.all().get();
            }

            log.info("Ultima mensagem do topico deletada com sucesso");
            return true;
        } catch (Exception e) {
            log.error("Erro ao deletar ultima mensagem do topico");
            throw new RuntimeException(e);
        }
    }
}
