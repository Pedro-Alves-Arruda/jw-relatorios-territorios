#!/bin/bash
set -e

# -----------------------------
# 1. Atualizar sistema e instalar dependências
# -----------------------------
sudo apt-get update
sudo apt-get install -y apt-transport-https ca-certificates curl software-properties-common unzip

# -----------------------------
# 2. Instalar Docker
# -----------------------------
if ! command -v docker &> /dev/null; then
  curl -fsSL https://get.docker.com -o get-docker.sh
  sh get-docker.sh
  sudo usermod -aG docker $USER
fi

# Instalar docker-compose (v2)
if ! command -v docker-compose &> /dev/null; then
  sudo apt-get install -y docker-compose-plugin
fi

# -----------------------------
# 3. Instalar Java (OpenJDK 17)
# -----------------------------
sudo apt-get install -y openjdk-17-jdk

# -----------------------------
# 4. Criar generate-certs.sh
# -----------------------------
cat > generate-certs.sh <<'EOF'
#!/bin/bash
set -e

PASSWORD=changeit
DAYS=365

mkdir -p certs-localhost
cd certs-localhost

echo "[1/9] Criando CA..."
openssl req -new -x509 -keyout ca.key -out ca.crt -days $DAYS -nodes -subj "/CN=localhost"
echo "01" > ca.srl

cat > san.cnf <<SAN
[req]
distinguished_name = req_distinguished_name
req_extensions = v3_req

[req_distinguished_name]

[v3_req]
subjectAltName = @alt_names

[alt_names]
DNS.1 = localhost
DNS.2 = kafka
DNS.3 = kafka-2
IP.1 = 127.0.0.1
SAN

echo "[2/9] Criando chave e CSR do broker..."
openssl req -new -nodes -keyout broker.key -out broker.csr -subj "/CN=localhost/OU=TI/O=Arruda/L=Betim/ST=MG/C=BR"

echo "[3/9] Assinando certificado do broker..."
openssl x509 -req -in broker.csr -CA ca.crt -CAkey ca.key -CAserial ca.srl -out broker.crt -days $DAYS -extfile san.cnf -extensions v3_req

echo "[4/9] Criando broker-keystore.p12..."
openssl pkcs12 -export -in broker.crt -inkey broker.key -out broker-keystore.p12 -name broker -CAfile ca.crt -caname root -password pass:$PASSWORD

echo "[5/9] Criando Keystore/Truststore do Kafka Server (JKS + PKCS12)..."
keytool -genkey -alias kafka-server -keyalg RSA -keystore kafka.server.keystore.jks -storepass $PASSWORD -keypass $PASSWORD -dname "CN=localhost,OU=TI,O=Arruda,L=Betim,ST=MG,C=BR"
keytool -certreq -alias kafka-server -keystore kafka.server.keystore.jks -file kafka-server.csr -storepass $PASSWORD
openssl x509 -req -in kafka-server.csr -CA ca.crt -CAkey ca.key -CAserial ca.srl -out kafka-server-signed.crt -days $DAYS -extfile san.cnf -extensions v3_req
keytool -import -alias CARoot -file ca.crt -keystore kafka.server.keystore.jks -storepass $PASSWORD -noprompt
keytool -import -alias kafka-server -file kafka-server-signed.crt -keystore kafka.server.keystore.jks -storepass $PASSWORD -noprompt
keytool -importkeystore -srckeystore kafka.server.keystore.jks -destkeystore kafka.server.keystore.p12 -deststoretype PKCS12 -srcstorepass $PASSWORD -deststorepass $PASSWORD -srcalias kafka-server -srckeypass $PASSWORD -destkeypass $PASSWORD -noprompt
cp kafka.server.keystore.jks kafka.keystore.jks
cp kafka.server.keystore.p12 kafka.keystore.p12

echo "[6/9] Criando Truststores do Kafka..."
keytool -import -alias CARoot -file ca.crt -keystore kafka.server.truststore.jks -storepass $PASSWORD -noprompt
keytool -import -alias CARoot -file ca.crt -keystore kafka.server.truststore.p12 -storetype PKCS12 -storepass $PASSWORD -noprompt
keytool -import -alias CARoot -file ca.crt -keystore kafka.truststore.jks -storepass $PASSWORD -noprompt
keytool -import -alias CARoot -file ca.crt -keystore kafka.truststore.p12 -storetype PKCS12 -storepass $PASSWORD -noprompt
openssl pkcs12 -export -in ca.crt -nokeys -out truststore.p12 -name CARoot -password pass:$PASSWORD

echo "[7/9] Criando Keystore/Truststore do Client (JKS + PKCS12)..."
keytool -genkey -alias client -keyalg RSA -keystore client.keystore.jks -storepass $PASSWORD -keypass $PASSWORD -dname "CN=client,OU=TI,O=Arruda,L=Betim,ST=MG,C=BR"
keytool -certreq -alias client -keystore client.keystore.jks -file client.csr -storepass $PASSWORD
openssl x509 -req -in client.csr -CA ca.crt -CAkey ca.key -CAserial ca.srl -out client-signed.crt -days $DAYS
keytool -import -alias CARoot -file ca.crt -keystore client.keystore.jks -storepass $PASSWORD -noprompt
keytool -import -alias client -file client-signed.crt -keystore client.keystore.jks -storepass $PASSWORD -noprompt
keytool -importkeystore -srckeystore client.keystore.jks -destkeystore client.keystore.p12 -deststoretype PKCS12 -srcstorepass $PASSWORD -deststorepass $PASSWORD -srcalias client -srckeypass $PASSWORD -destkeypass $PASSWORD -noprompt

echo "[8/9] Criando Truststore do Client..."
keytool -import -alias CARoot -file ca.crt -keystore client.truststore.jks -storepass $PASSWORD -noprompt
keytool -import -alias CARoot -file ca.crt -keystore client.truststore.p12 -storetype PKCS12 -storepass $PASSWORD -noprompt

echo "Importando meus subjects names para kafka-server para que ele reconheça eles"
openssl x509 -req -in Kafka-server.csr -CA ca.crt -CAkey ca.key -CAcreateserial \
  -out kafka.crt -days 365 -extfile san.cnf -extensions v3_req

echo "[9/9] Finalizado!"
echo "✅ Certificados gerados em ./certs-localhost"
EOF

chmod +x generate-certs.sh

# -----------------------------
# 5. Executar generate-certs.sh
# -----------------------------
./generate-certs.sh

# -----------------------------
# 6. Ajustar permissões para Docker
# -----------------------------
sudo chown -R 1001:1001 ./certs-localhost
sudo chmod -R 600 ./certs-localhost/*

# -----------------------------
# 7. Criar docker-compose.yml
# -----------------------------
cat > docker-compose.yml <<'EOF'
version: '3.8'

services:

  zookeeper:
    image: bitnami/zookeeper:latest
    container_name: zookeeper
    ports:
      - "2181:2181"
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes

  kafka-1:
    image: pedroarruda/kafka-certs-localhost
    container_name: kafka-1
    restart: always
    ports:
      - "9094:9094"
    depends_on:
      - zookeeper
    volumes:
      - /home/ubuntu/certs-localhost:/bitnami/kafka/config/certs:ro
      - kafka_data:/bitnami/kafka/data
    environment:
      # Broker ID e ZooKeeper
      - KAFKA_CFG_BROKER_ID=1
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181

      # Listeners e protocolos
      - KAFKA_CFG_LISTENERS=SSL_INTERNAL://:9094,SSL_EXTERNAL://:9095
      - KAFKA_CFG_ADVERTISED_LISTENERS=SSL_INTERNAL://kafka:9094,SSL_EXTERNAL://3.91.100.218:9095
      - KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=SSL_INTERNAL:SSL,SSL_EXTERNAL:SSL
      - KAFKA_CFG_SECURITY_INTER_BROKER_PROTOCOL=SSL

      # SSL
      - KAFKA_CFG_SSL_KEYSTORE_TYPE=PKCS12
      - KAFKA_CFG_SSL_KEYSTORE_LOCATION=/bitnami/kafka/config/certs/kafka.server.keystore.p12
      - KAFKA_CFG_SSL_KEYSTORE_PASSWORD=changeit
      - KAFKA_CFG_SSL_KEY_PASSWORD=changeit

      - KAFKA_CFG_SSL_TRUSTSTORE_TYPE=PKCS12
      - KAFKA_CFG_SSL_TRUSTSTORE_LOCATION=/bitnami/kafka/config/certs/kafka.server.truststore.p12
      - KAFKA_CFG_SSL_TRUSTSTORE_PASSWORD=changeit

      # Exige que o cliente apresente certificado
      - KAFKA_CFG_SSL_CLIENT_AUTH=required

    networks:
      - kafka-network

    kafka-2:
      image: pedroarruda/kafka-certs-localhost
      container_name: kafka-2
      restart: always
      ports:
        - "9096:9096"
      depends_on:
        - zookeeper
      volumes:
        - /home/ubuntu/certs-localhost:/bitnami/kafka/config/certs:ro
        - kafka_data:/bitnami/kafka/data
      environment:
        # Broker ID e ZooKeeper
        - KAFKA_CFG_BROKER_ID=1
        - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181

        # Listeners e protocolos
        - KAFKA_CFG_LISTENERS=SSL_INTERNAL://:9096,SSL_EXTERNAL://:9097
        - KAFKA_CFG_ADVERTISED_LISTENERS=SSL_INTERNAL://kafka:9096,SSL_EXTERNAL://3.91.100.218:9097
        - KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=SSL_INTERNAL:SSL,SSL_EXTERNAL:SSL
        - KAFKA_CFG_SECURITY_INTER_BROKER_PROTOCOL=SSL

        # SSL
        - KAFKA_CFG_SSL_KEYSTORE_TYPE=PKCS12
        - KAFKA_CFG_SSL_KEYSTORE_LOCATION=/bitnami/kafka/config/certs/kafka.server.keystore.p12
        - KAFKA_CFG_SSL_KEYSTORE_PASSWORD=changeit
        - KAFKA_CFG_SSL_KEY_PASSWORD=changeit

        - KAFKA_CFG_SSL_TRUSTSTORE_TYPE=PKCS12
        - KAFKA_CFG_SSL_TRUSTSTORE_LOCATION=/bitnami/kafka/config/certs/kafka.server.truststore.p12
        - KAFKA_CFG_SSL_TRUSTSTORE_PASSWORD=changeit

        # Exige que o cliente apresente certificado
        - KAFKA_CFG_SSL_CLIENT_AUTH=required

      networks:
        - kafka-network

    backend:
      image: pedroarruda/jw-relatorios:latest
      container_name: "Backend"
      ports:
        - "8080:8080"
      volumes:
        - /home/ubuntu/certs-localhost:/bitnami/kafka/config/certs:ro

    frontend:
      image: pedroarruda:jw-relatorios-frontend
      container_name:  "Frontend"
      ports:
        - "4200:4200"


volumes:
  kafka_data:
networks:
  kafka-network:
    drivers:bridge
EOF

# -----------------------------
# . Subir containers
# -----------------------------
sudo docker compose up -d

echo "✅ Kafka, Zookeeper, Java-Backend e Angular-Frontend foram iniciados com sucesso!"
