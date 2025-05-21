# Servicio de Precios - Inditex

Este proyecto implementa un servicio REST para la gestión de precios de productos siguiendo la arquitectura hexagonal (DDD).

## Tecnologías

- Java 17
- Spring Boot 3.1.0
- H2 Database
- Flyway para migraciones
- Lombok
- MapStruct
- Spring Data JPA

## Configuración

El proyecto usa:
- Base de datos H2 en memoria
- Flyway para migraciones
- Consola H2 disponible en `/h2-console`

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/inditex/pricing/
│   └── resources/
│       ├── application.properties
│       └── db/migration/
│           ├── V1__create_prices_table.sql
│           └── V2__insert_initial_data.sql
```
