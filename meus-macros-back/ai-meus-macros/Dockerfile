# Estágio 1: Build da aplicação
FROM maven:3.8.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências (cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o restante do código fonte e realiza o build
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Criação da imagem final para execução
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia o jar gerado no estágio de build para a imagem final
COPY --from=build /app/target/ai-meus-macros-0.0.1-SNAPSHOT.jar app.jar

# Caso a aplicação utilize uma porta específica, exponha-a (aqui usamos 8080 como exemplo)
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
