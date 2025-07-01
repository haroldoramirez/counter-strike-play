# Etapa 1: build com sbt
FROM hseeberger/scala-sbt:11.0.20_1.9.7_2.13.12 AS builder

WORKDIR /app
COPY . .

# Compila e prepara o binário
RUN sbt stage

# Etapa 2: imagem leve só com o app
FROM openjdk:11-jre-slim

WORKDIR /app

# Copia o app empacotado da etapa anterior
COPY --from=builder /app/target/universal/stage /app

# Expõe a porta padrão do Play
EXPOSE 9000

# Define o comando para rodar a aplicação
CMD ["./bin/my-play-app", "-Dplay.http.secret.key=mySecretKey", "-Dhttp.port=9000"]