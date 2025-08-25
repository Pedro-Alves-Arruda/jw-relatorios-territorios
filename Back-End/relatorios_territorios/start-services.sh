docker run -d \
  --name kafka-2 \
  --restart always \
  -p 9094:9094 \
  -v ./certs:/bitnami/kafka/config/certs \
  -e KAFKA_CFG_NODE_ID="0" \
  -e KAFKA_CFG_PROCESS_ROLES="controller,broker" \
  -e KAFKA_CFG_LISTENERS="SASL_SSL://0.0.0.0:9094,CONTROLLER://0.0.0.0:9093" \
  -e KAFKA_CFG_ADVERTISED_LISTENERS="SASL_SSL://localhost:9094" \
  -e KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP="CONTROLLER:PLAINTEXT,SASL_SSL:SASL_SSL" \
  -e KAFKA_CFG_CONTROLLER_QUORUM_VOTERS="0@kafka:9093" \
  -e KAFKA_CFG_CONTROLLER_LISTENER_NAMES="CONTROLLER" \
  -e KAFKA_CFG_SASL_MECHANISM_INTER_BROKER_PROTOCOL="SCRAM-SHA-512" \
  -e KAFKA_CFG_SASL_ENABLED_MECHANISMS="SCRAM-SHA-512" \
  bitnami/kafka:latest

echo "Containers Kafka iniciado em background!"
echo "Kafka SASL_SSL: localhost:9094"