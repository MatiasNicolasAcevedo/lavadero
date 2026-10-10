# Arquitectura

## Componentes (actual)

```mermaid
flowchart LR
  U[Navegador] -->|HTTPS| FE["client/ · React SPA<br/>Vercel"]
  FE -->|"fetch + Bearer JWT<br/>/v1/api/*"| BE["server/ · Spring Boot<br/>Render (Docker)"]
  BE -->|JPA / JDBC| DB[("PostgreSQL 16")]
  BE -. "keep-alive cada 14 min<br/>(ServerWakeUpScheduler)" .-> BE
```

En local: front en Vite (:5173), back en :8080, Postgres en Docker (:5432). Hoy el front local llama a la API de producción (T-003).

## Flujo de una request

```mermaid
sequenceDiagram
  participant C as Cliente (SPA)
  participant F as JwtFilter
  participant S as SecurityFilterChain
  participant Ctl as Controller
  participant Svc as Service
  participant R as Repository
  C->>F: GET /v1/api/clientes (Authorization: Bearer …)
  F->>F: valida firma y expiración (JwtService), carga User por email
  F->>S: SecurityContext autenticado
  S->>Ctl: autorizado (anyRequest().authenticated())
  Ctl->>Svc: getAllClientes()
  Svc->>R: findAll()
  R-->>Svc: List<Cliente>
  Svc-->>Ctl: List<ClienteResponseDTO> (MapStruct)
  Ctl-->>C: 200 JSON
```

- Capas: `controller` (HTTP + `@Valid`) → `service` (interfaz + `impl/`, reglas de negocio, `ResponseStatusException`) → `repository` (Spring Data JPA). DTOs separados de las entidades.
- Errores: `exception/GlobalExceptionHandler.java` → `{"error": "..."}` o `{"campo": "mensaje"}` para validación. Errores no manejados → 403 (T-013).

## Autenticación

1. `POST /v1/api/auth/register` o `/auth/login` (públicos) → `AuthenticationManager` + BCrypt → JWT HS256 firmado con `SECRET`, subject = email, expira en 10 días (`config/jwt/JwtService.java`).
2. El front guarda `token` y `user` en `localStorage` (`client/src/services/Api.tsx`) y manda `Authorization: Bearer` en cada request.
3. `JwtFilter` autentica cada request; sesión `STATELESS`, CSRF deshabilitado, CORS `*` (`config/security/SecurityConfig.java`, `config/CorsConfig.java`).
4. No hay roles: todo usuario autenticado puede todo.

## Modelo de datos

```mermaid
erDiagram
  USUARIOS {
    long id PK
    string email
    string password "BCrypt"
    string firstName
    string lastName
    string phone
  }
  CLIENTES ||--o{ VEHICULOS : tiene
  VEHICULOS ||--o{ TURNOS : tiene
  TURNOS ||--o| COBROS : "se cobra"
  CLIENTES {
    long id PK
    string nombre
    string correoElectronico UK
    string telefono
  }
  VEHICULOS {
    long id PK
    string modelo
    string matricula
    enum tipo "SEDAN|SUV|CAMIONETA"
    jsonb propiedadesAdicionales
    long cliente_id FK
  }
  TURNOS {
    long id PK
    datetime fechaHora
    enum estado "PENDIENTE|EN_PROCESO|FINALIZADO"
    enum tipoServicio "LAVADO_EXTERIOR|INTERIOR|COMPLETO"
    long vehiculo_id FK
  }
  COBROS {
    long id PK
    decimal monto
    date fecha
    long turno_id FK,UK
  }
```

`usuarios` (operadores del sistema) no se relaciona con `clientes` (dueños de vehículos). Esquema generado por Hibernate (`ddl-auto=create`), sin migraciones.

## Endpoints

Base `/v1/api`. Todos requieren JWT salvo los marcados 🔓. Contrato vivo: `/v3/api-docs` (Swagger UI en `/swagger-ui/index.html`).

| Método | Ruta | Qué hace | Front |
|---|---|---|---|
| POST 🔓 | `/auth/register` | Crea usuario y devuelve JWT | Register |
| POST 🔓 | `/auth/login` | Devuelve JWT + datos del usuario | Login |
| GET 🔓 | `/render/health` | Health check (texto) | — |
| GET / POST | `/clientes` | Listar / crear cliente | Dashboard |
| GET | `/clientes/{id}` | Cliente con sus vehículos | ClientVehicles |
| POST | `/vehiculos` | Crear vehículo de un cliente | ClientVehicles |
| GET | `/vehiculos/{id}` · `/vehiculos/cliente/{clienteId}` | Vehículo · vehículos de un cliente | Turnos · ClientVehicles |
| POST | `/turnos` | Crear turno (fecha futura) | Turnos |
| GET | `/turnos/{id}` · `/turnos/vehiculo/{vehiculoId}` | Turno · turnos de un vehículo | Turnos |
| PUT | `/turnos/{id}/en-proceso` · `/turnos/{id}/finalizado` | Cambiar estado | Turnos |
| POST | `/cobros` | Cobrar turno FINALIZADO (uno por turno) | Turnos |
| POST | `/demo` | Endpoint de prueba (sobrante) | — |

## Arquitectura objetivo (resumen; detalle en ADRs y Backlog)

- Perfiles `dev` / `prod` con esquema versionado (Flyway) y seed solo en dev (T-014).
- Errores con `ProblemDetail` y 401/403 correctos (T-013).
- Front con capa de API única (`VITE_API_URL`, manejo de 401) (T-003 + Backlog).
- Tests de integración con Testcontainers ([ADR 0001](adr/0001-testcontainers-postgres-over-h2.md)); actualización Boot 3.3 → 3.5 → 4.x ([ADR 0002](adr/0002-spring-boot-upgrade-path.md)).
