# Etapa de construção
FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

# Expor a porta 8080
EXPOSE 8080

# Etapa final (produzindo a imagem)
FROM openjdk:21-jdk-slim

# Copiar o JAR gerado na etapa de build
COPY --from=build /target/Integrador-api-wc-0.0.1-SNAPSHOT.jar app.jar

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
