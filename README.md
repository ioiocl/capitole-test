# Pricing Service - Inditex

Este servicio proporciona una API REST para consultar precios de productos según la marca, el producto y la fecha de aplicación.

## Arquitectura

El proyecto sigue los principios de la Arquitectura Hexagonal (Ports and Adapters) y Domain-Driven Design (DDD), con la siguiente estructura:

```
src/
├── main/
│   └── java/
│       └── com/
│           └── inditex/
│               └── pricing/
│                   ├── domain/
│                   │   ├── model/
│                   │   └── port/
│                   │       ├── in/
│                   │       └── out/
│                   ├── infrastructure/
│                   │   └── adapter/
│                   │       ├── in/
│                   │       │   └── rest/
│                   │       └── out/
│                   │           └── persistence/
│                   └── PricingApplication.java
```

## Tecnologías

- Java 17
- Spring Boot 3.1.0
- Spring Data JPA
- H2 Database
- Flyway para migraciones
- MapStruct para mapeos
- JUnit 5 y AssertJ para testing

## API REST

### Consultar Precio

```
GET /api/v1/prices?brandId={brandId}&productId={productId}&applicationDate={applicationDate}
```

#### Parámetros

- `brandId`: ID de la marca (Integer)
- `productId`: ID del producto (Integer)
- `applicationDate`: Fecha de aplicación (ISO DateTime, e.g., "2020-06-14T10:00:00")

#### Ejemplo de Respuesta

```json
{
    "brandId": 1,
    "productId": 35455,
    "priceList": 1,
    "startDate": "2020-06-14T00:00:00",
    "endDate": "2020-12-31T23:59:59",
    "price": 35.50,
    "currency": "EUR"
}
```

## Base de Datos

La base de datos H2 incluye una tabla `PRICES` con la siguiente estructura:

```sql
CREATE TABLE PRICES (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id BIGINT,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    price_list BIGINT,
    product_id BIGINT,
    priority INT,
    price DECIMAL(10,2),
    curr VARCHAR(3)
);
```

## Tests

El proyecto incluye tests unitarios y de integración que cubren los siguientes escenarios:

1. Petición a las 10:00 del día 14 para el producto 35455 y la marca 1
2. Petición a las 16:00 del día 14 para el producto 35455 y la marca 1
3. Petición a las 21:00 del día 14 para el producto 35455 y la marca 1
4. Petición a las 10:00 del día 15 para el producto 35455 y la marca 1
5. Petición a las 21:00 del día 16 para el producto 35455 y la marca 1

## Ejecución

### Usando Maven

1. Clonar el repositorio
2. Ejecutar: `mvn clean install`
3. Iniciar la aplicación: `mvn spring-boot:run`
4. La API estará disponible en: `http://localhost:8080`

### Usando Docker

1. Construir la imagen:
   ```bash
   docker build -t pricing-service .
   ```

2. Ejecutar el contenedor:
   ```bash
   docker run -p 8080:8080 pricing-service
   ```

   O usando docker-compose:
   ```bash
   docker-compose up -d
   ```

3. La API estará disponible en: `http://localhost:8080`

## Consideraciones de Diseño

- Se utiliza Arquitectura Hexagonal para mantener el dominio aislado de los detalles de infraestructura
- Se implementa el patrón Repository para el acceso a datos
- Se utiliza el patrón DTO para las respuestas de la API
- Los precios se ordenan por prioridad para seleccionar el más adecuado
- Se incluye manejo global de excepciones
- Se implementan validaciones de entrada
- Se utiliza Flyway para gestionar las migraciones de base de datos
- MapStruct
- Spring Data JPA

## Configuración

El proyecto usa:
- Base de datos H2 en memoria
- Flyway para migraciones
- Consola H2 disponible en `/h2-console`

