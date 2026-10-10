# Lavadero · server

API REST en Spring Boot 3.3 (Java 17) con PostgreSQL y JWT. Visión general y paso a paso completo: [README raíz](../README.md).

## Correr solo el back

Todos los comandos desde `server/`, con JDK 17 (`java -version` → `17.x`).

**1. Configuración local** (una vez, sin editar):

```bash
cp env.properties.example src/main/resources/env.properties
```

Resultado esperado: existe `server/src/main/resources/env.properties` (ignorado por git).

**2. Base de datos:**

```bash
docker compose up -d --wait
```

Resultado esperado: `Container lavadero-postgres Healthy`.

**3. Arrancar:**

```bash
./mvnw spring-boot:run
```

Resultado esperado: `Started LavaderoApplication`. Chequeo: `curl http://localhost:8080/v1/api/render/health` → `Server is awake and running!`.

**4. Tests** (necesitan la base del paso 2 hasta T-006):

```bash
./mvnw test
```

Resultado esperado: `Tests run: 17, Failures: 0, Errors: 0` y `BUILD SUCCESS`.

## Comandos

| Comando | Para qué |
|---|---|
| `./mvnw spring-boot:run` | Levantar en :8080 (recrea el esquema y carga el seed) |
| `./mvnw test` | Todos los tests |
| `./mvnw test -Dtest=ClienteServiceImplTest` | Una clase de test |
| `./mvnw test -Dtest='ClienteServiceImplTest#createCliente_whenEmailExists_throws400'` | Un método de test |
| `./mvnw -B clean verify` | Build completo (lo que corre `scripts/verify.sh --server`) |
| `./mvnw -DskipTests package` | Generar `target/lavadero-0.0.1-SNAPSHOT.jar` |
| `docker compose down -v` | Apagar la base y borrar sus datos |

## Estructura

```
src/main/java/tech/munidigital/lavadero/
  config/        CorsConfig, OpenApiConfig, DataInitializer (seed) · jwt/ (JwtFilter, JwtService) · security/ (SecurityConfig, AppConfig)
  controller/    Endpoints REST bajo /v1/api (auth, clientes, vehiculos, turnos, cobros, render/health)
  dto/           request/ (con Bean Validation) y response/
  entity/        Entidades JPA + enums/
  exception/     GlobalExceptionHandler
  mappers/       Interfaces MapStruct
  repository/    Spring Data JPA
  service/       Interfaces + impl/
  util/          ServerWakeUpScheduler (keep-alive de Render)
src/main/resources/application.properties
src/test/java/…/service/impl/   Tests unitarios de servicios (Mockito)
```

Variables de entorno y alternativas sin Docker: [docs/SETUP.md](../docs/SETUP.md). Reglas para agentes: [AGENTS.md](AGENTS.md).
