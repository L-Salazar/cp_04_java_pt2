# Use uma imagem base com JDK 17
FROM openjdk:17-jdk-slim AS build

# Defina o diretório de trabalho no container
WORKDIR /app

# Copie o arquivo pom.xml para baixar as dependências
COPY pom.xml .

# Baixe as dependências Maven
RUN mvn dependency:go-offline

# Copie o código fonte para o container
COPY src /app/src

# Compile o projeto com Maven
RUN mvn clean package -DskipTests

# A partir de uma imagem mais leve para rodar a aplicação
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho no container
WORKDIR /app

# Copie o JAR do build
COPY --from=build /app/target/performancekids-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponha a porta 8081 (ou a porta que você está usando)
EXPOSE 8081

# Comando para rodar o aplicativo Spring Boot
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
