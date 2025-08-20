#!/bin/bash

# === CARREGAR VARIÁVEIS DO .env ===
if [ -f .env ]; then
  echo "📄 Carregando variáveis do .env..."
  source .env
else
  echo "❌ Arquivo .env não encontrado!"
  exit 1
fi

# === VALIDAR VARIÁVEIS ===
if [[ -z "$DOCKER_USERNAME" || -z "$IMAGE_NAME" || -z "$TAG" ]]; then
  echo "❌ Variáveis DOCKER_USERNAME, IMAGE_NAME ou TAG não estão definidas no .env."
  exit 1
fi

# === BUILD ===
echo "📦 Construindo a imagem Docker..."
docker build -t jw-relatorios-web .

# === RETAG ===
echo "🔄 Aplicando tag para o Docker Hub..."
docker tag jw-relatorios-web $DOCKER_USERNAME/$IMAGE_NAME:$TAG

# === LOGIN ===
echo "🔐 Logando no Docker Hub..."
docker login

# === PUSH ===
echo "🚀 Enviando imagem para o Docker Hub..."
docker push $DOCKER_USERNAME/$IMAGE_NAME:$TAG

echo "✅ Imagem publicada com sucesso: https://hub.docker.com/r/$DOCKER_USERNAME/$IMAGE_NAME"
