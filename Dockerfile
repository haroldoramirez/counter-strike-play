# Dockerfile

FROM openjdk:11

# Instala o sbt
RUN apt-get update && apt-get install -y curl && \
    curl -L -o sbt.deb https://github.com/sbt/sbt/releases/download/v1.9.7/sbt-1.9.7.deb && \
    apt install ./sbt.deb && rm sbt.deb

WORKDIR /app

COPY . .

RUN sbt compile

EXPOSE 9000

CMD ["sbt", "run"]