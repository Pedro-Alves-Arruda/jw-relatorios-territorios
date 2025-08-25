#!/bin/bash
set -e

PASSWORD=changeit
DAYS=365

# Diretório de saída
mkdir -p certs-localhost
cd certs-localhost

echo "[1/9] Criando CA..."
openssl req -new -x509 -keyout ca.key -out ca.crt -days $DAYS -nodes \
  -subj "//CN=localhost"
echo "01" > ca.srl

cat > san.cnf <<EOF
[req]
distinguished_name = req_distinguished_name
req_extensions = v3_req

[req_distinguished_name]

[v3_req]
subjectAltName = @alt_names

[alt_names]
DNS.1 = localhost
IP.1 = 127.0.0.1
EOF

echo "[2/9] Criando chave e CSR do broker..."
openssl req -new -nodes -keyout broker.key -out broker.csr \
  -subj "//CN=localhost//OU=TI//O=Arruda//L=Betim//ST=MG//C=BR"

echo "[3/9] Assinando certificado do broker..."
openssl x509 -req -in broker.csr -CA ca.crt -CAkey ca.key -CAserial ca.srl \
  -out broker.crt -days $DAYS -extfile san.cnf -extensions v3_req

echo "[4/9] Criando broker-keystore.p12..."
openssl pkcs12 -export -in broker.crt -inkey broker.key \
  -out broker-keystore.p12 -name broker -CAfile ca.crt -caname root -password pass:$PASSWORD

echo "[5/9] Criando Keystore/Truststore do Kafka Server (JKS + PKCS12)..."
keytool -genkey -alias kafka-server -keyalg RSA -keystore kafka.server.keystore.jks \
  -storepass $PASSWORD -keypass $PASSWORD -dname "CN=localhost,OU=TI,O=Arruda,L=Betim,ST=MG,C=BR"

keytool -certreq -alias kafka-server -keystore kafka.server.keystore.jks \
  -file kafka-server.csr -storepass $PASSWORD

openssl x509 -req -in kafka-server.csr -CA ca.crt -CAkey ca.key -CAserial ca.srl \
  -out kafka-server-signed.crt -days $DAYS -extfile san.cnf -extensions v3_req

keytool -import -alias CARoot -file ca.crt -keystore kafka.server.keystore.jks \
  -storepass $PASSWORD -noprompt

keytool -import -alias kafka-server -file kafka-server-signed.crt \
  -keystore kafka.server.keystore.jks -storepass $PASSWORD -noprompt

# Converter para PKCS12
keytool -importkeystore -srckeystore kafka.server.keystore.jks \
  -destkeystore kafka.server.keystore.p12 -deststoretype PKCS12 \
  -srcstorepass $PASSWORD -deststorepass $PASSWORD -srcalias kafka-server -srckeypass $PASSWORD -destkeypass $PASSWORD -noprompt

cp kafka.server.keystore.jks kafka.keystore.jks
cp kafka.server.keystore.p12 kafka.keystore.p12

echo "[6/9] Criando Truststores do Kafka..."
keytool -import -alias CARoot -file ca.crt -keystore kafka.server.truststore.jks \
  -storepass $PASSWORD -noprompt

keytool -import -alias CARoot -file ca.crt -keystore kafka.server.truststore.p12 \
  -storetype PKCS12 -storepass $PASSWORD -noprompt

keytool -import -alias CARoot -file ca.crt -keystore kafka.truststore.jks \
  -storepass $PASSWORD -noprompt

keytool -import -alias CARoot -file ca.crt -keystore kafka.truststore.p12 \
  -storetype PKCS12 -storepass $PASSWORD -noprompt

openssl pkcs12 -export -in ca.crt -nokeys -out truststore.p12 \
  -name CARoot -password pass:$PASSWORD

echo "[7/9] Criando Keystore/Truststore do Client (JKS + PKCS12)..."
keytool -genkey -alias client -keyalg RSA -keystore client.keystore.jks \
  -storepass $PASSWORD -keypass $PASSWORD -dname "CN=client,OU=TI,O=Arruda,L=Betim,ST=MG,C=BR"

keytool -certreq -alias client -keystore client.keystore.jks \
  -file client.csr -storepass $PASSWORD

openssl x509 -req -in client.csr -CA ca.crt -CAkey ca.key -CAserial ca.srl \
  -out client-signed.crt -days $DAYS

keytool -import -alias CARoot -file ca.crt -keystore client.keystore.jks \
  -storepass $PASSWORD -noprompt

keytool -import -alias client -file client-signed.crt \
  -keystore client.keystore.jks -storepass $PASSWORD -noprompt

# Converter client para PKCS12
keytool -importkeystore -srckeystore client.keystore.jks \
  -destkeystore client.keystore.p12 -deststoretype PKCS12 \
  -srcstorepass $PASSWORD -deststorepass $PASSWORD -srcalias client -srckeypass $PASSWORD -destkeypass $PASSWORD -noprompt

echo "[8/9] Criando Truststore do Client..."
keytool -import -alias CARoot -file ca.crt -keystore client.truststore.jks \
  -storepass $PASSWORD -noprompt

keytool -import -alias CARoot -file ca.crt -keystore client.truststore.p12 \
  -storetype PKCS12 -storepass $PASSWORD -noprompt

echo "[9/9] Finalizado!"
echo "✅ Foram gerados certificados em JKS e PKCS12 (p12) no diretório ./certs-localhost"
