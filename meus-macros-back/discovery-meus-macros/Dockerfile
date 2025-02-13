# Etapa 1: Build
FROM maven:3.9.4-eclipse-temurin-17 as builder
WORKDIR /app
COPY . .
RUN mvn clean install

# Etapa 2: Runtime
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/discovery-meus-macros-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]