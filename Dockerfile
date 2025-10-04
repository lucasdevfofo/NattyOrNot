# 1. Imagem base com Java 21
FROM eclipse-temurin:21-jdk-alpine

# 2. Diretório dentro do container
WORKDIR /app

# 3. Copiar o JAR gerado para o container
COPY target/NattyOrNot-0.0.1-SNAPSHOT.jar app.jar

# 4. Expõe a porta que o Spring Boot vai usar
EXPOSE 8080

# 5. Comando para rodar o JAR
ENTRYPOINT ["java","-jar","app.jar"]
