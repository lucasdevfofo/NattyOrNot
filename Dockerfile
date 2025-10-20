# Etapa de build
FROM maven:3.9.7-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copia o JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Render define PORT automaticamente
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
