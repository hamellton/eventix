# eventix-portfolio (Java + Hibernate + Liquibase)

## Что внутри
- Spring Boot (Web + Spring Data JPA)
- Hibernate 6 (через JPA)
- Liquibase миграции в `src/main/resources/db/changelog`
- PostgreSQL
- Интеграционный тест (Testcontainers) проверяет, что Liquibase создал таблицы и JPA работает

## Локальный запуск (PostgreSQL)
1) Поднимите PostgreSQL на localhost:5432 и создайте БД/пользователя:
- db: `eventix`
- user: `eventix`
- password: `eventix`

2) Запуск:
```bash
mvn -q spring-boot:run
```

## Тесты
Интеграционные тесты поднимают PostgreSQL в Docker (Testcontainers):
```bash
mvn test
```

## Миграции Liquibase
- `db/changelog/001-create-users.yaml`
- `db/changelog/002-create-events.yaml`

Изменения подключаются в `db/changelog/db.changelog-master.yaml`

