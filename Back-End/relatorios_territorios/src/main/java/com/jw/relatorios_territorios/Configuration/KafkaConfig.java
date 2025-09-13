package com.jw.relatorios_territorios.Configuration;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.*;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclBindingFilter;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.internals.Topic;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.apache.kafka.common.quota.ClientQuotaFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.time.Duration;
import java.util.*;

@Configuration
public class KafkaConfig {

    //Publicador
    @Bean
    public NewTopic publicadorSalvar(){return TopicBuilder.name("publicador-salvar")
            .partitions(1)
            .replicas(2)
            .build();}

    @Bean
    public NewTopic publicadorSalvarFotoPerfil(){
        return TopicBuilder. name("publicador-salvar-foto-perfil")
                .partitions(1)
                .replicas(2)
                .build();
    }


    //Revisita
    @Bean
    public NewTopic revisitaSalvar() { return TopicBuilder.name("revisita-salvar")
            .partitions(1)
            .replicas(2)
            .build(); }
    @Bean
    public NewTopic revisitaAtualizar() { return TopicBuilder.name("revisita-atualizar")
            .partitions(1)
            .replicas(2)
            .build(); }

    //Relatorio
    @Bean
    public NewTopic relatorioSalvar(){ return TopicBuilder.name("relatorio-salvar")
            .partitions(1)
            .replicas(2)
            .build(); }


    //login
    @Bean
    public NewTopic redefinicaoSenha(){ return TopicBuilder.name("redefinicao-senha")
            .partitions(1)
            .replicas(2)
            .build(); }

    @Bean
    public NewTopic atualizarSenha(){ return TopicBuilder.name("atualizar-senha")
            .partitions(1)
            .replicas(2)
            .build(); }


    //Bean para capturar as mensagens do topico kafka e apagar ela caso seja necessario
    @Bean
    public AdminClient adminClient(){
        return new AdminClient() {
            @Override
            public void close(Duration duration) {

            }

            @Override
            public CreateTopicsResult createTopics(Collection<NewTopic> collection, CreateTopicsOptions createTopicsOptions) {
                return null;
            }

            @Override
            public DeleteTopicsResult deleteTopics(TopicCollection topicCollection, DeleteTopicsOptions deleteTopicsOptions) {
                return null;
            }

            @Override
            public ListTopicsResult listTopics(ListTopicsOptions listTopicsOptions) {
                return null;
            }

            @Override
            public DescribeTopicsResult describeTopics(TopicCollection topicCollection, DescribeTopicsOptions describeTopicsOptions) {
                return null;
            }

            @Override
            public DescribeClusterResult describeCluster(DescribeClusterOptions describeClusterOptions) {
                return null;
            }

            @Override
            public DescribeAclsResult describeAcls(AclBindingFilter aclBindingFilter, DescribeAclsOptions describeAclsOptions) {
                return null;
            }

            @Override
            public CreateAclsResult createAcls(Collection<AclBinding> collection, CreateAclsOptions createAclsOptions) {
                return null;
            }

            @Override
            public DeleteAclsResult deleteAcls(Collection<AclBindingFilter> collection, DeleteAclsOptions deleteAclsOptions) {
                return null;
            }

            @Override
            public DescribeConfigsResult describeConfigs(Collection<ConfigResource> collection, DescribeConfigsOptions describeConfigsOptions) {
                return null;
            }

            @Override
            public AlterConfigsResult alterConfigs(Map<ConfigResource, Config> map, AlterConfigsOptions alterConfigsOptions) {
                return null;
            }

            @Override
            public AlterConfigsResult incrementalAlterConfigs(Map<ConfigResource, Collection<AlterConfigOp>> map, AlterConfigsOptions alterConfigsOptions) {
                return null;
            }

            @Override
            public AlterReplicaLogDirsResult alterReplicaLogDirs(Map<TopicPartitionReplica, String> map, AlterReplicaLogDirsOptions alterReplicaLogDirsOptions) {
                return null;
            }

            @Override
            public DescribeLogDirsResult describeLogDirs(Collection<Integer> collection, DescribeLogDirsOptions describeLogDirsOptions) {
                return null;
            }

            @Override
            public DescribeReplicaLogDirsResult describeReplicaLogDirs(Collection<TopicPartitionReplica> collection, DescribeReplicaLogDirsOptions describeReplicaLogDirsOptions) {
                return null;
            }

            @Override
            public CreatePartitionsResult createPartitions(Map<String, NewPartitions> map, CreatePartitionsOptions createPartitionsOptions) {
                return null;
            }

            @Override
            public DeleteRecordsResult deleteRecords(Map<TopicPartition, RecordsToDelete> map, DeleteRecordsOptions deleteRecordsOptions) {
                return null;
            }

            @Override
            public CreateDelegationTokenResult createDelegationToken(CreateDelegationTokenOptions createDelegationTokenOptions) {
                return null;
            }

            @Override
            public RenewDelegationTokenResult renewDelegationToken(byte[] bytes, RenewDelegationTokenOptions renewDelegationTokenOptions) {
                return null;
            }

            @Override
            public ExpireDelegationTokenResult expireDelegationToken(byte[] bytes, ExpireDelegationTokenOptions expireDelegationTokenOptions) {
                return null;
            }

            @Override
            public DescribeDelegationTokenResult describeDelegationToken(DescribeDelegationTokenOptions describeDelegationTokenOptions) {
                return null;
            }

            @Override
            public DescribeConsumerGroupsResult describeConsumerGroups(Collection<String> collection, DescribeConsumerGroupsOptions describeConsumerGroupsOptions) {
                return null;
            }

            @Override
            public ListConsumerGroupsResult listConsumerGroups(ListConsumerGroupsOptions listConsumerGroupsOptions) {
                return null;
            }

            @Override
            public ListConsumerGroupOffsetsResult listConsumerGroupOffsets(Map<String, ListConsumerGroupOffsetsSpec> map, ListConsumerGroupOffsetsOptions listConsumerGroupOffsetsOptions) {
                return null;
            }

            @Override
            public DeleteConsumerGroupsResult deleteConsumerGroups(Collection<String> collection, DeleteConsumerGroupsOptions deleteConsumerGroupsOptions) {
                return null;
            }

            @Override
            public DeleteConsumerGroupOffsetsResult deleteConsumerGroupOffsets(String s, Set<TopicPartition> set, DeleteConsumerGroupOffsetsOptions deleteConsumerGroupOffsetsOptions) {
                return null;
            }

            @Override
            public ElectLeadersResult electLeaders(ElectionType electionType, Set<TopicPartition> set, ElectLeadersOptions electLeadersOptions) {
                return null;
            }

            @Override
            public AlterPartitionReassignmentsResult alterPartitionReassignments(Map<TopicPartition, Optional<NewPartitionReassignment>> map, AlterPartitionReassignmentsOptions alterPartitionReassignmentsOptions) {
                return null;
            }

            @Override
            public ListPartitionReassignmentsResult listPartitionReassignments(Optional<Set<TopicPartition>> optional, ListPartitionReassignmentsOptions listPartitionReassignmentsOptions) {
                return null;
            }

            @Override
            public RemoveMembersFromConsumerGroupResult removeMembersFromConsumerGroup(String s, RemoveMembersFromConsumerGroupOptions removeMembersFromConsumerGroupOptions) {
                return null;
            }

            @Override
            public AlterConsumerGroupOffsetsResult alterConsumerGroupOffsets(String s, Map<TopicPartition, OffsetAndMetadata> map, AlterConsumerGroupOffsetsOptions alterConsumerGroupOffsetsOptions) {
                return null;
            }

            @Override
            public ListOffsetsResult listOffsets(Map<TopicPartition, OffsetSpec> map, ListOffsetsOptions listOffsetsOptions) {
                return null;
            }

            @Override
            public DescribeClientQuotasResult describeClientQuotas(ClientQuotaFilter clientQuotaFilter, DescribeClientQuotasOptions describeClientQuotasOptions) {
                return null;
            }

            @Override
            public AlterClientQuotasResult alterClientQuotas(Collection<ClientQuotaAlteration> collection, AlterClientQuotasOptions alterClientQuotasOptions) {
                return null;
            }

            @Override
            public DescribeUserScramCredentialsResult describeUserScramCredentials(List<String> list, DescribeUserScramCredentialsOptions describeUserScramCredentialsOptions) {
                return null;
            }

            @Override
            public AlterUserScramCredentialsResult alterUserScramCredentials(List<UserScramCredentialAlteration> list, AlterUserScramCredentialsOptions alterUserScramCredentialsOptions) {
                return null;
            }

            @Override
            public DescribeFeaturesResult describeFeatures(DescribeFeaturesOptions describeFeaturesOptions) {
                return null;
            }

            @Override
            public UpdateFeaturesResult updateFeatures(Map<String, FeatureUpdate> map, UpdateFeaturesOptions updateFeaturesOptions) {
                return null;
            }

            @Override
            public DescribeMetadataQuorumResult describeMetadataQuorum(DescribeMetadataQuorumOptions describeMetadataQuorumOptions) {
                return null;
            }

            @Override
            public UnregisterBrokerResult unregisterBroker(int i, UnregisterBrokerOptions unregisterBrokerOptions) {
                return null;
            }

            @Override
            public DescribeProducersResult describeProducers(Collection<TopicPartition> collection, DescribeProducersOptions describeProducersOptions) {
                return null;
            }

            @Override
            public DescribeTransactionsResult describeTransactions(Collection<String> collection, DescribeTransactionsOptions describeTransactionsOptions) {
                return null;
            }

            @Override
            public AbortTransactionResult abortTransaction(AbortTransactionSpec abortTransactionSpec, AbortTransactionOptions abortTransactionOptions) {
                return null;
            }

            @Override
            public ListTransactionsResult listTransactions(ListTransactionsOptions listTransactionsOptions) {
                return null;
            }

            @Override
            public FenceProducersResult fenceProducers(Collection<String> collection, FenceProducersOptions fenceProducersOptions) {
                return null;
            }

            @Override
            public ListClientMetricsResourcesResult listClientMetricsResources(ListClientMetricsResourcesOptions listClientMetricsResourcesOptions) {
                return null;
            }

            @Override
            public Uuid clientInstanceId(Duration duration) {
                return null;
            }

            @Override
            public AddRaftVoterResult addRaftVoter(int i, Uuid uuid, Set<RaftVoterEndpoint> set, AddRaftVoterOptions addRaftVoterOptions) {
                return null;
            }

            @Override
            public RemoveRaftVoterResult removeRaftVoter(int i, Uuid uuid, RemoveRaftVoterOptions removeRaftVoterOptions) {
                return null;
            }

            @Override
            public Map<MetricName, ? extends Metric> metrics() {
                return Map.of();
            }
        };
    }


    //campo
    @Bean
    public NewTopic servicoCampo(){
        return TopicBuilder
                .name("servico-campo")
                .replicas(2)
                .partitions(1)
                .build();
    }
 }
