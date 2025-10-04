# Imagem com Maven + JDK 21
FROM maven:3.9.2-eclipse-temurin-21 AS build

# Diretório de trabalho
WORKDIR /app

# Copia arquivos do projeto
COPY pom.xml .
COPY src ./src

# Build do JAR
RUN mvn clean package -DskipTests

# Segunda etapa: imagem mais leve para rodar
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiar o JAR gerado
COPY --from=build /app/target/NattyOrNot-0.0.1-SNAPSHOT.jar app.jar

# Porta
EXPOSE 8080

# Rodar o JAR
ENTRYPOINT ["java","-jar","app.jar"]
