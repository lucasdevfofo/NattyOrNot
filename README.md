# API NattyOrNot

Uma API RESTful para gerenciar **Bomb**, **Supplement** e **Rat**, com relações e regras de negócio simples.

---

## 🔎 Sobre o Projeto

A **API NattyOrNot** é um backend em **Java 21** com **Spring Boot 3.5** para centralizar o cadastro e o controle de itens (bombas e suplementos) e seus usos por “rats”. A aplicação expõe uma API REST organizada em camadas (controller → service → repository), com execução simples via **H2 em memória** para desenvolvimento e opção de **PostgreSQL** containerizado com **Docker** para portabilidade e escalabilidade.

**Stack**: Java 21 · Spring Boot · Spring Web · Spring Data JPA · Lombok · H2 · PostgreSQL · Docker

---

## 🚀 Funcionalidades Principais

* **Gestão de Bomb**: CRUD completo.
* **Gestão de Supplement**: CRUD completo.
* **Gestão de Rat**: CRUD completo com vínculos a Bomb e Supplement.
* **Regras de Negócio**: se `useBomb = true`, o campo `bomb` é obrigatório; se `useSupplement = true`, a lista `supplements` deve conter IDs válidos.
* **Arquitetura Limpa**: camadas `controller`, `service` e `repository` + entidades do domínio.
* **Ambiente Containerizado (opcional)**: banco PostgreSQL e (se desejar) a API com Docker/Docker Compose.

---

## ✅ Pré‑requisitos

Escolha um dos caminhos:

**Caminho A — Docker (recomendado para PostgreSQL)**

* Docker
* Docker Compose

**Caminho B — Local (H2)**

* Java 21 (Temurin/Adoptium)
* Maven (wrapper `./mvnw` incluso)

---

## ⚙️ Instalação e Execução

### Caminho A) Com Docker (PostgreSQL)

1. Crie/ajuste o `docker-compose.yml` (exemplo):

```yaml
services:
  db:
    image: postgres:16
    environment:
      POSTGRES_DB: db-nattyornot
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
  pgadmin:
    image: dpage/pgadmin4
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@admin.com
      PGADMIN_DEFAULT_PASSWORD: admin
    ports:
      - "5050:80"
```

2. Suba os containers:

```bash
docker compose up -d
```

3. Configure um perfil `postgres` (ex.: `src/main/resources/application-postgres.properties`):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/db-nattyornot
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

4. Rode a API com o perfil:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
# ou
./mvnw clean package && java -jar target/NattyOrNot-0.0.1-SNAPSHOT.jar --spring.profiles.active=postgres
```

### Caminho B) Local (H2 – zero dependências externas)

1. Use o `application.properties` padrão (H2 em memória):

```properties
spring.datasource.url=jdbc:h2:mem:nattyornotdb
spring.datasource.username=sa
spring.datasource.password=sa
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

2. Execute:

```bash
./mvnw spring-boot:run
```

3. Endereços úteis:

* API: `http://localhost:8080`
* H2 Console: `http://localhost:8080/h2-console` (JDBC: `jdbc:h2:mem:nattyornotdb`, user `sa`, senha `sa`)

---

## 📚 Documentação da API

### 🔧 Recurso: Bomb (`/bomb`)

| Método | Rota          | Descrição                             |
| ------ | ------------- | ------------------------------------- |
| GET    | `/bomb`       | Lista todas as bombas.                |
| GET    | `/bomb/{id}`  | Retorna uma bomba pelo ID.            |
| POST   | `/bomb/criar` | Cria uma nova bomba.                  |
| PUT    | `/bomb/{id}`  | Atualiza todos os dados de uma bomba. |
| PATCH  | `/bomb/{id}`  | Atualiza parcialmente uma bomba.      |
| DELETE | `/bomb/{id}`  | Remove uma bomba específica.          |
| DELETE | `/bomb`       | Remove **todas** as bombas.           |

**Exemplo de body (POST/PUT):**

```json
{
  "name": "Thermo X",
  "type": "termogenico",
  "price": 129.90,
  "description": "Bomba muito doida",
  "manufacture": "2025-01-15",
  "mortal": false,
  "weight": "300g",
  "scoopWeight": "10g"
}
```

---

### 🔧 Recurso: Supplement (`/supplement`)

| Método | Rota                | Descrição                                         |
|--------|---------------------|---------------------------------------------------|
| GET    | `/supplement`       | Lista todos os suplementos.                       |
| GET    | `/supplement/{id}`  | Retorna um suplemento pelo ID.                    |
| POST   | `/supplement/criar` | Cria um novo suplemento.   <br/>                  |
| PUT    | `/supplement/{id`   | Atualiza todos os dados de um suplemento.   <br/> |
| PATCH  | `/supplement/{id`   | Atualiza parcialmente um suplemento.    <br/>     |
| DELETE | `/supplement/{id}`  | Remove um suplemento específico.                  |
| DELETE | `/supplement`       | Remove **todos** os suplementos.                  |

**Exemplo de body (POST):**

```json
{
  "name": "Creatina",
  "type": "monohidratada",
  "price": 89.90,
  "description": "Creatina pura",
  "manufacture": "2025-02-01",
  "weight": "300g",
  "scoopWeight": "3g"
}
```

---

### 🔧 Recurso: Rat (`/rat`)

| Método | Rota         | Descrição                                 |
|--------| ------------ |-------------------------------------------|
| GET    | `/rat`       | Lista todos.                              |
| GET    | `/rat/{id}`  | Retorna um Rat pelo ID.                   |
| POST   | `/rat/criar` | Cria um novo Rat.                         |
| PUT    | `/rat/{id}`  | Atualiza todos os dados de um Rat.        |
| PATCH  | `/rat/{id}`  | Atualiza parcialmente os dados de um Rat. |
| DELETE | `/rat/{id}`  | Remove um Rat específico.                 |
| DELETE | `/rat`       | Remove **todos** os Rats.                 |

**Exemplo de body (POST/PUT):**

```json
{
  "name": "Lucas",
  "useBomb": true,
  "useSupplement": true,
  "bomb": { "id": 1 },
  "supplements": [{ "id": 2 }, { "id": 3 }],
  "apLocation": "Academia do Zé"
}
```

> **Regras**: se `useBomb=true` e `bomb` for omitido → **400 Bad Request**. Se `useSupplement=true`, `supplements` deve conter IDs existentes.

---

## 🗂️ Estrutura do Projeto

```
src/main/java/com/NattyOrNot
 ├─ controler  
 ├─ exception            
 ├─ model     
 ├─ repository        
 ├─ service         

```

---

## 🛠️ Troubleshooting

* **H2 console não abre** → confirme `spring.h2.console.enabled=true` e o path `/h2-console`.
* **PostgreSQL não conecta** → verifique containers (`docker compose ps`), porta `5432` e credenciais.
* **Porta 8080 ocupada** → troque `server.port` ou finalize o processo ocupando a porta.
* **`ddl-auto=update`** é só para dev; em produção, use migrações (ex.: Flyway).

##   API está hospedada em:
➡️ https://nattyornot.onrender.com

## ☁️ Deploy e Banco

**Banco de dados: PostgreSQL**

- **Console H2:** `https://nattyornot.onrender.com/h2-console`
- **Driver Class:** `org.postgresql.Driver`
- **JDBC URL:** `jdbc:postgresql://dpg-d3gi8o8gjchc739q0scg-a:5432/nattyornotdb`
- **Usuário:** `nattyornotdb_user`
- **Senha:** `kgn9IqczneZ11LW7HBNtkRnkvrcNAzOK`
- **Status:** ✅ Test successful (conexão verificada)





