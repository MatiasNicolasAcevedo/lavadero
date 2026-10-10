# 0001 · Tests de integración con Testcontainers PostgreSQL, no H2

- Estado: Aceptada · Fecha: 2026-10-10 · Tareas: T-006

## Contexto

`contextLoads` (único test de integración) necesita hoy un Postgres levantado a mano y variables de entorno; sin eso falla (`Cannot load driver class: ${DB_DRIVER_CLASS_NAME}`). La entidad `Vehiculo` usa `@JdbcTypeCode(SqlTypes.JSON)` con `columnDefinition = "jsonb"` (`server/src/main/java/tech/munidigital/lavadero/entity/Vehiculo.java:48-50`), específico de PostgreSQL. Producción usa PostgreSQL 16.

## Decisión

Los tests `@SpringBootTest` y `@DataJpaTest` corren contra **PostgreSQL 16 en Testcontainers** (`@ServiceConnection`), con un perfil `test` que no depende de `env.properties`. No se usa H2.

## Alternativas

- **H2 en modo PostgreSQL**: más rápido y sin Docker, pero no soporta `jsonb` igual y oculta diferencias de dialecto → falsos verdes.
- **Postgres externo (docker compose) como hoy**: funciona, pero acopla los tests al entorno y a datos que deja el seed.
- **Embedded Postgres (zonky)**: sin Docker, pero binarios nativos por plataforma y menos estándar.

## Consecuencias

- Docker es prerrequisito para `verify.sh` y CI (ya lo es para el desarrollo local; `ubuntu-latest` en GitHub Actions lo trae).
- El primer arranque descarga la imagen `postgres:16-alpine` (~100 MB); se reutiliza el contenedor entre clases de test con el contexto cacheado de Spring.
- `verify.sh` deja de levantar la base con docker compose para el back (se simplifica en T-006).
