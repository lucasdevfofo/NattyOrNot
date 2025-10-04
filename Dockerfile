# Etapa de build com Maven e Oracle JDK 21
FROM maven:3.9.11-oraclelinux8 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa final: imagem leve Oracle JDK 21
FROM oracle/graalvm-ce:21-java21

WORKDIR /app

COPY --from=build /app/target/NattyOrNot-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
