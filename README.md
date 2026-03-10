# Random User API

Spring Boot proxy service that fetches random user data from the [randomuser.me](https://randomuser.me) API.

## Tech Stack

- Java 21
- Spring Boot 3.5
- Spring Web (RestClient)
- Resilience4j (Circuit Breaker + Retry)
- Bean Validation
- SpringDoc OpenAPI (Swagger UI)
- Spring Boot Actuator

## API Endpoints

| Method | Path              | Description                              | Parameters                    |
|--------|-------------------|------------------------------------------|-------------------------------|
| GET    | `/api/users`      | Fetch random users                       | `count` (default: 50, min: 1) |
| GET    | `/api/gender-data` | Fetch random users grouped by gender    | `count` (default: 50, min: 1) |

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.9+

### Run

```bash
./mvnw spring-boot:run
```

The application starts on `http://localhost:8080`.

### Run Tests

```bash
./mvnw test
```

## Configuration

Key properties in `application.properties`:

| Property                  | Default                    | Description                  |
|---------------------------|----------------------------|------------------------------|
| `randomuser.base-url`    | `https://randomuser.me`    | External API base URL        |
| `randomuser.max-count`   | `5000`                     | Max users per request        |
| `randomuser.connect-timeout` | `2000`                 | Connection timeout (ms)      |
| `randomuser.read-timeout`    | `3000`                 | Read timeout (ms)            |

## Resilience

The external API client is protected with Resilience4j:

- **Retry** — up to 3 attempts with 1s delay on server errors and connectivity issues
- **Circuit Breaker** — opens after 50% failure rate over a sliding window of 10 calls; waits 30s before half-open

Circuit breaker health is exposed via Actuator at `/actuator/health`.

## API Docs

Swagger UI is available at `/swagger-ui.html` when the application is running.
