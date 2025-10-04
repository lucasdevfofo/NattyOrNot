# Etapa de build
FROM maven:3.9.11-oraclelinux8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa runtime
FROM oraclelinux:9-slim
WORKDIR /app

# Instalação do JDK 21 manualmente
RUN microdnf install -y curl tar gzip && \
    curl -L -o /tmp/jdk21.tar.gz https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.tar.gz && \
    tar -xzf /tmp/jdk21.tar.gz -C /opt && \
    rm /tmp/jdk21.tar.gz
ENV JAVA_HOME=/opt/jdk-21
ENV PATH=$JAVA_HOME/bin:$PATH

# Copiar JAR do build
COPY --from=build /app/target/NattyOrNot-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
