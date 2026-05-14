# eventix-portfolio (Java + Hibernate + Liquibase)

## What's inside
- Spring Boot (Web + Spring Data JPA)
- Hibernate 6 (via JPA)
- Liquibase migrations in `src/main/resources/db/changelog`
- PostgreSQL
- Integration test (Testcontainers) verifies that Liquibase created the tables and JPA works

## Local run (PostgreSQL)
1) Start PostgreSQL on localhost:5432 and create the database/user:
- db: `eventix`
- user: `eventix`
- password: `eventix`

2) Run:
```bash
mvn -q spring-boot:run
```

## Docker Compose (server + postgres + Adminer)
Run:
```bash
docker compose up --build -d
```

Endpoints:
- Server (Spring Boot): http://localhost:8080
- Adminer: http://localhost:8081

Adminer will open and you can add a connection to the Postgres container (host: `eventix-db` / port: `5432`, if needed).



## Tests
Integration tests spin up PostgreSQL in Docker (Testcontainers):
```bash
mvn test
```

## Liquibase migrations
- `db/changelog/001-create-users.yaml`
- `db/changelog/002-create-events.yaml`

Changes are included in `db/changelog/db.changelog-master.yaml`

