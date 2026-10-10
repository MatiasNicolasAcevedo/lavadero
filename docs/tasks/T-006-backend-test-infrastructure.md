# T-006 · Infraestructura de tests del back: Testcontainers + JaCoCo
- Estado: Lista · Prioridad: P0 · Esfuerzo: M · Agente: 🧠 frontera
- Fase: P0 Estabilizar
- Rama: `test/T-006-backend-test-infrastructure` · Depende de: — · Spec/ADR: [0001](../adr/0001-testcontainers-postgres-over-h2.md)
- Revisa: ligero (segunda mirada) + dueño

## Por qué
`contextLoads` necesita Postgres levantado a mano y variables (auditoría §3, §4.7); no hay cobertura medida en el build ni ejemplo de test de integración para que copien las tareas 🤖.

## Objetivo
`./mvnw verify` corre todos los tests sin `env.properties` ni base previa (solo Docker), mide cobertura y falla si baja del umbral.

## Alcance
- Incluye: dependencias `spring-boot-testcontainers` + `org.testcontainers:postgresql` + `junit-jupiter` (versiones del BOM de Boot 3.3); `TestcontainersConfiguration` con `@ServiceConnection PostgreSQLContainer<>("postgres:16-alpine")`; `src/test/resources/application-test.properties` (driver, dialect, `SECRET` de test); `@ActiveProfiles("test")`; plugin `jacoco-maven-plugin` con `prepare-agent`, `report` y `check` (LINE ≥ 0.52, BRANCH ≥ 0.09 a nivel BUNDLE); primer test de integración `AuthFlowIT` (login con el usuario del seed → 200 con token; `GET /v1/api/clientes` con ese token → 200).
- Actualizar `scripts/verify.sh` (el back ya no levanta docker compose; sí chequea `docker info`), `docs/TESTING.md` (umbrales, archivo de referencia de integración), `AGENTS.md`.
- **No incluye:** desactivar el scheduler (T-014), cambiar códigos HTTP (T-013), tests de controllers con `@WebMvcTest` (T-013), CI (T-011).

## Leer antes
- ADR 0001 · `server/pom.xml` L32–171 · `server/src/test/java/tech/munidigital/lavadero/LavaderoApplicationTests.java`
- `server/src/main/resources/application.properties` L1–19 · `scripts/verify.sh` función `verify_server`

## Archivos previstos
- modificar: `server/pom.xml`, `LavaderoApplicationTests.java`, `scripts/verify.sh`, `docs/TESTING.md`, `AGENTS.md`, `server/AGENTS.md`
- crear: `server/src/test/java/tech/munidigital/lavadero/TestcontainersConfiguration.java`, `server/src/test/resources/application-test.properties`, `server/src/test/java/tech/munidigital/lavadero/AuthFlowIT.java` (+ `maven-failsafe-plugin` si se separan `*IT`)

## Tests
- `AuthFlowIT` (`@SpringBootTest(webEnvironment = RANDOM_PORT)` + `TestRestTemplate`): login demo → 200 + token; endpoint protegido con token → 200; sin token → 403 (caracterización; T-013 lo cambia a 401).

## Pasos sugeridos
1. Dependencias y config de Testcontainers; `contextLoads` en verde sin `env.properties` (moverlo a `/tmp` para probar).
2. JaCoCo: medir, confirmar ≥ 52 % / 9 %, fijar umbral redondeando hacia abajo, publicar `target/site/jacoco/` como referencia.
3. `AuthFlowIT`.
4. `verify.sh` y docs.

## Criterios de aceptación
- [ ] Sin `server/src/main/resources/env.properties` y sin contenedor previo: `cd server && ./mvnw -B clean verify` → `BUILD SUCCESS`
- [ ] `target/site/jacoco/index.html` generado; bajar el umbral a mano a 0.99 hace fallar `jacoco:check` (probar y revertir)
- [ ] `AuthFlowIT` pasa y es el archivo de referencia de integración en `docs/TESTING.md`
- [ ] `./scripts/verify.sh --server` en verde en < 2 min con dependencias cacheadas

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: … · Pendientes → nuevas tareas: …
