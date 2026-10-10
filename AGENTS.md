# AGENTS.md — cómo trabajar en este repo

Lavadero: sistema de gestión para un lavadero de autos (usuarios con JWT, clientes, vehículos, turnos, cobros). Monorepo: API REST Spring Boot en `server/`, front React en `client/`. Prueba técnica de 2025 que se está retomando como pieza de portfolio.

```
server/   Spring Boot 3.3 · Java 17 · PostgreSQL · Maven (./mvnw)      → server/AGENTS.md
client/   React 18 · TypeScript · Vite 5 · Tailwind · NextUI          → client/AGENTS.md
scripts/  verify.sh (gate) · dev.sh (entorno local)
docs/     STATUS, BACKLOG, ROADMAP, TESTING, ARCHITECTURE, SETUP, tasks/, specs/, adr/, audits/, agents/
```

## Stack (versiones reales)

Java 17 (JDK 21 rompe el build hasta T-002) · Spring Boot 3.3.1 · Spring Security 6.3 · jjwt 0.12.5 · MapStruct 1.5.5 · Lombok 1.18.26 · PostgreSQL 16 · Node 22 · React 18.3 · Vite 5.2 · TypeScript 5.4 · Tailwind 3.4 · ESLint 8.

## Comandos verificados

| Qué | Comando (desde la raíz salvo indicación) |
|---|---|
| Setup completo | [README § Paso a paso](README.md#paso-a-paso) (no se repite acá) |
| Entorno local | `./scripts/dev.sh` (`--down` apaga la base) |
| **Gate de verificación** | `./scripts/verify.sh` · solo una parte: `--server` / `--client` |
| Logs del gate | `.verify-logs/<paso>.log` (leer solo las últimas líneas) |
| Un test del back | `cd server && ./mvnw -q test -Dtest='ClaseTest#metodo'` |
| Un test del front | no hay runner todavía (T-007); después: `cd client && npx vitest run ruta/al.test.tsx` |
| E2E | no existe todavía (T-012) |

El back necesita Postgres en :5432 para `contextLoads` (`verify.sh` lo levanta con Docker si no está).

## Protocolo de sesión

1. Al empezar: leé `docs/STATUS.md` y `docs/BACKLOG.md`, y listá los PR abiertos (`gh pr list` o MCP de GitHub).
2. Una sola tarea por sesión, en su rama `<tipo>/T-NNN-slug`, con PR en draft desde el primer commit.
3. Tests en el mismo PR que el cambio. Nada de "lo testeo después".
4. Al terminar: `./scripts/verify.sh` en verde, tarea + tablero + `STATUS.md` actualizados en el PR, PR listo para revisión, y sugerir sesión nueva.
5. Protocolos completos: implementar → [docs/agents/implementer.md](docs/agents/implementer.md) · revisar → [reviewer.md](docs/agents/reviewer.md) · auditar → [auditor.md](docs/agents/auditor.md).

## Gestión de contexto

1. **Una tarea = una sesión.** Al cerrar una tarea, revisión o auditoría no sigas con otra: sugerí abrir una sesión nueva (`/clear` o conversación nueva). Entre tareas, sesión nueva siempre es mejor que compactar.
2. **Sugerí compactar** (`/compact` en Claude Code; `/compress` en Qwen Code, o sesión nueva) solo a mitad de un trabajo largo y si: se cerró una sub-etapa (diagnóstico → implementación); se acumularon logs, stack traces o archivos que ya no hacen falta; el uso de contexto pasa ~60 % (frontera) o ~50 % (agente ligero); o hay señales de degradación (releer archivos, olvidar decisiones, repetir intentos fallidos).
3. **Antes de sugerirlo, persistí:** "Notas de implementación" de la tarea (hecho / pendiente / próximo paso), commit y push.
4. **Cómo:** detenete y dale al usuario la instrucción lista para copiar: `/compact Conservar: tarea T-NNN, rama y PR, decisiones, archivos modificados, estado de verify y próximo paso. Descartar: logs y contenido de archivos ya procesados.` y «después escribime "seguí con T-NNN"».
5. **No compactes** justo antes de terminar (terminá primero) ni entre tareas (sesión nueva).
6. **Higiene:** salida acotada (`-q`, `| tail -n 60`); grep/glob antes de abrir archivos y leé rangos de líneas; no abras `package-lock.json`, `target/`, `node_modules/`, `dist/`, `.verify-logs/` completos ni `docs/archive/`; no pegues archivos enteros en las respuestas; respuestas finales cortas.

## Mapa de documentación

| Archivo | Qué contiene | Cuándo leerlo |
|---|---|---|
| `docs/STATUS.md` | Dónde estamos, salud, próximo paso | Siempre, al empezar |
| `docs/BACKLOG.md` | Tablero de tareas | Siempre, al empezar |
| `docs/tasks/T-NNN-*.md` | Spec de una tarea | Al tomar esa tarea |
| `docs/TESTING.md` | Estrategia y convenciones de tests | Al escribir tests |
| `CONTRIBUTING.md` | Ramas, commits, PR, revisión | Al abrir rama/PR o revisar |
| `docs/ARCHITECTURE.md` | Componentes, modelo, endpoints | Si la tarea toca estructura |
| `docs/SETUP.md` | Variables, sin Docker, IDE, problemas | Si el entorno falla |
| `docs/ROADMAP.md` | Fases e hitos | Auditorías y replanificación |
| `docs/specs/`, `docs/adr/` | Features multi-tarea y decisiones | Solo si la tarea los enlaza |
| `docs/audits/` | Auditorías fechadas | **No leer** salvo la última, en auditorías |
| `docs/archive/` | Tareas, specs y logs viejos | **No leer** salvo que la tarea lo indique |

## Convenciones actuales (copiá el estilo de estos archivos)

- Back: capas `controller → service (interfaz + impl/) → repository`; DTOs `XRequestDTO`/`XResponseDTO` con Bean Validation y mapper MapStruct; errores con `ResponseStatusException` (mensajes en español). Indentación 2 espacios, Javadoc en español.
  - Controller: `server/src/main/java/tech/munidigital/lavadero/controller/VehiculoController.java`
  - Servicio: `server/src/main/java/tech/munidigital/lavadero/service/impl/VehiculoServiceImpl.java`
  - Test unitario de servicio: `server/src/test/java/tech/munidigital/lavadero/service/impl/ClienteServiceImplTest.java`
  - Test de controller / integración: no hay todavía (T-006, T-013).
- Front: componentes funcionales `.tsx`, Tailwind en `className`, `fetch` con `Authorization: Bearer ${token}` del `useAuth()`. Página de referencia: `client/src/pages/dashboard/Dashboard.tsx`. Tests: no hay todavía (T-007).

## Git (detalle en [CONTRIBUTING.md](CONTRIBUTING.md))

- Rama corta por tarea desde `main`: `<tipo>/T-NNN-slug`. Nunca commitear a `main`.
- Conventional Commits en inglés: `tipo(alcance): descripción` + footer `Refs: T-NNN`.
- Título del PR = Conventional Commit (lo valida CI); merge con squash, lo hace el dueño.
- Revisión siempre en una sesión distinta a la que implementó.

## Límites: no tocar sin una tarea que lo pida

- Versiones mayores de dependencias, Java, Spring Boot, React, Node.
- Seguridad y autenticación (`server/.../config/security`, `config/jwt`, `CorsConfig`), secretos, `application.properties` de prod.
- Deploy (`server/Dockerfile`, `client/vercel.json`), CI (`.github/workflows/`), umbrales de cobertura (solo suben).
- Esquema de base de datos y datos seed (`config/DataInitializer.java`).
- Borrar, saltear (`@Disabled`, `.skip`) o debilitar tests. Commitear a `main`. Mergear sin revisión aprobada.

## Gotchas del entorno

- **JDK 21+ rompe la compilación** (Lombok 1.18.26): usá JDK 17 (`sdk env install`) hasta T-002. `verify.sh` lo avisa.
- `ddl-auto=create`: cada arranque del back **borra y recrea** el esquema y el seed vuelve a cargar los datos.
- El front llama a la API de **producción** (URL hardcodeada) hasta T-003.
- Cualquier error no manejado del back devuelve **403 vacío** (`/error` protegido) hasta T-013: mirá el log del back.
- `ServerWakeUpScheduler` hace un GET a Render cada 14 min también en local y en tests (falla sin red: es ruido, no un error del test).
- Los `.sh` necesitan bash (Git Bash o WSL en Windows).
