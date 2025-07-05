# Etapa 1: build com sbt
# FROM hseeberger/scala-sbt:11.0.20_1.9.7_2.13.12 AS builder
FROM hseeberger/scala-sbt:graalvm-ce-21.3.0-java17_1.6.2_3.1.1 AS builder

WORKDIR /app
COPY . .

# Compila e prepara o binário
RUN sbt stage

# Etapa 2: imagem leve só com o app
FROM openjdk:17

WORKDIR /app

# Copia o app empacotado da etapa anterior
COPY --from=builder /app/target/universal/stage /app

# Expõe a porta padrão do Play
EXPOSE 8080

# Define o comando para rodar a aplicação
CMD ["./bin/counter-strike-play", "-Dplay.http.secret.key=Cb9NkaOsqoFu1/l8wt/39TFMmKLTH2cYeIl5TMGxTdoInyKTMxwXY1+efU5jM+ToOdJEnlfVsfkxQ/ZqTHkKJA==", "-Dhttp.port=8080"]