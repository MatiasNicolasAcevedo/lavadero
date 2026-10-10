# server/AGENTS.md — específico del back

Leé primero el [AGENTS.md raíz](../AGENTS.md). Esto agrega solo lo del back.

- Comandos desde `server/` con JDK 17: `./mvnw -q test -Dtest='XTest#metodo'` (rápido, sin base) · `./mvnw test` (todos; `contextLoads` necesita Postgres) · gate: `../scripts/verify.sh --server`.
- Paquete base `tech.munidigital.lavadero`. Endpoints bajo `/v1/api/...`; públicos solo `auth/*`, `render/*`, Swagger (`config/security/SecurityConfig.java:81-88`).
- Nuevo endpoint: DTO request con validaciones (`dto/request/`), DTO response, método en mapper MapStruct (`mappers/`), interfaz + impl en `service/`, controller con `@Tag`/`@Operation` de springdoc y `@Valid`.
- Errores: `throw new ResponseStatusException(HttpStatus.X, "mensaje en español")`; `exception/GlobalExceptionHandler.java` los convierte en `{"error": "..."}`.
- Tests de servicio: `@ExtendWith(MockitoExtension.class)`, `@Mock` repos y mappers, `@InjectMocks` el impl, AssertJ, nombres `metodo_cuandoCondicion_resultado` (ver `src/test/.../ClienteServiceImplTest.java`).
- Fechas: hoy se usa `LocalDateTime.now()` directo; en tests usá fechas relativas (`now().plusDays(1)`) hasta que exista `Clock` (Backlog).
- Archivos caros: `config/DataInitializer.java` (312 líneas, seed): leé solo el rango que necesites.
- No toques `application.properties`, `config/security/`, `config/jwt/`, `CorsConfig`, `Dockerfile` ni el `pom.xml` (salvo lo que la tarea pida explícitamente).
