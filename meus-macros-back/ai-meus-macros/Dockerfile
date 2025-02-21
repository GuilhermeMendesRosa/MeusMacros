# Etapa 1: Build
FROM maven:3.9.4-eclipse-temurin-17 as builder
WORKDIR /app
COPY . .

# Definindo a variável de ambiente dentro do container
ENV OPENAI_API_KEY="chave-fake"

RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=builder /app/target/ai-meus-macros-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
