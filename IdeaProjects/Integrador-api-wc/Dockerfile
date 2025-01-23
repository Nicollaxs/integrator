# Use a imagem base do Ubuntu
FROM ubuntu:latest AS build

# Atualiza pacotes e instala Java 21
RUN apt-get update
RUN apt-get install openjdk-21-jdk -y

# Instala o Maven
RUN apt-get install maven -y

# Define o diretório de trabalho para o build
WORKDIR /app

# Copia os arquivos do código-fonte para o contêiner
COPY src .

# Executa o Maven para construir o projeto
RUN mvn clean install

# Exponha a porta 8080
EXPOSE 8080

# Copia o .jar gerado pelo Maven para o contêiner final
COPY --from=build /target/Integrador-api-wc-0.0.1-SNAPSHOT.jar app.jar

# Define o ponto de entrada do contêiner
ENTRYPOINT ["java", "-jar", "app.jar"]
