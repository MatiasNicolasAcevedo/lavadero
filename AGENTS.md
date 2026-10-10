# AGENTS.md — cómo trabajar en este repo

Lavadero: sistema de gestión para un lavadero de autos (usuarios con JWT, clientes, vehículos, turnos, cobros). Monorepo: API REST Spring Boot en `server/`, front React en `client/`. Prueba técnica de 2025 que se está retomando como pieza de portfolio.

```
server/   Spring Boot 3.3 · Java 17 · PostgreSQL · Maven (./mvnw)      → server/AGENTS.md
client/   React 18 · TypeScript · Vite 5 · Tailwind · NextUI          → client/AGENTS.md
docker-compose.yml   db siempre · server y client con el profile `app`
scripts/  verify.sh (gate) · dev.sh (entorno local)
docs/     STATUS, BACKLOG, ROADMAP, TESTING, ARCHITECTURE, SETUP, tasks/, specs/, adr/, audits/, agents/
```

## Ejecutores

Cada tarea, revisión o paso dice quién lo hace (`Ejecuta:` / `Revisa:`):

| Ícono | Ejecutor | Qué es | Para qué |
|---|---|---|---|
| 🧠 | **Modelo de frontera** | Claude Code u otro modelo potente | Infra de tests/CI/contenedores, seguridad, arquitectura, migraciones, specs y tareas, revisar lo riesgoso, auditar |
| 🤖 | **Agente gratuito (IntelliJ)** | Agente de IA gratuito integrado en IntelliJ IDEA, modelo Qwen. **Sin `gh` ni MCP** (solo `git` en la terminal del IDE), **sin `/compact`**, contexto ~64K | Tareas chicas y bien especificadas, de a una, con sus tests; revisar PRs chicos sin riesgo |
| 👤 | **Dueño** | El dueño del repo (persona) | Decidir, configurar GitHub, rotar secretos, abrir los PR del 🤖, mergear y **cambiar de agente** |

Ningún agente lanza a otro ejecutor (excepción: el sub-agente revisor, `CONTRIBUTING.md` § 4). Si el próximo paso es de otro ejecutor, **te detenés** y dejás el traspaso.

### Traspasos

Al terminar una tarea, revisión o auditoría, o si no podés seguir, tu respuesta final y `docs/STATUS.md` → "Foco" dicen:

> **Próximo paso:** T-NNN · <título> · **ejecuta: 🤖 agente gratuito (IntelliJ)** | **🧠 modelo de frontera** | **👤 dueño**
> **Qué hacer:** abrí <un chat nuevo del agente de IntelliJ | una sesión nueva de Claude Code | GitHub → …> y escribí: «Leé `AGENTS.md` y seguí `docs/agents/implementer.md`» (o el protocolo que corresponda).

Pasa **siempre** al 🧠: la primera tarea de la cola es 🧠; la matriz asigna la revisión a 🧠; el 🤖 escaló (2 intentos fallidos, decisión de diseño o fuera de alcance); hace falta una auditoría.

## Stack (versiones reales)

Java 17 (JDK 21 rompe el build hasta T-002) · Spring Boot 3.3.1 · Spring Security 6.3 · jjwt 0.12.5 · MapStruct 1.5.5 · Lombok 1.18.26 · PostgreSQL 16 · Node 22 · React 18.3 · Vite 5.2 · TypeScript 5.4 · Tailwind 3.4 · ESLint 8.

## Comandos verificados

| Qué | Comando (desde la raíz salvo indicación) |
|---|---|
| Setup completo | [README § Levantar el proyecto en local](README.md#levantar-el-proyecto-en-local) (no se repite acá) |
| Solo la base | `docker compose up -d --wait` |
| Todo en contenedores | `docker compose --profile app up -d --build` · apagar: `docker compose --profile app down -v` |
| Entorno de desarrollo | `./scripts/dev.sh` (`--down` apaga la base) |
| **Gate de verificación** | `./scripts/verify.sh` · solo una parte: `--server` / `--client` |
| Logs del gate | `.verify-logs/<paso>.log` (leer solo las últimas líneas) |
| Un test del back | `cd server && ./mvnw -q test -Dtest='ClaseTest#metodo'` |
| Un test del front | no hay runner todavía (T-007); después: `cd client && npx vitest run ruta/al.test.tsx` |
| E2E | no existe todavía (T-012) |

El back necesita Postgres en :5432 para `contextLoads` (`verify.sh` lo levanta con el compose si no está).

## Protocolo de sesión

1. Al empezar: leé `docs/STATUS.md` y `docs/BACKLOG.md` y mirá los PR abiertos (`gh pr list`, MCP; sin `gh`: "PR abierto" de `STATUS.md` y `git ls-remote --heads origin`).
2. **Una tarea a la vez:** si hay un PR de tarea abierto, no se empieza otra (se atiende o se traspasa su revisión/merge).
3. Tomá **siempre la primera de la cola** ("Listas"). Si no te corresponde (🧠 o 👤), no saltees: dejá el traspaso y terminá.
4. **Rama con el nombre exacto de la tarea antes del primer commit**; PR en draft enseguida (🤖: link de comparación para el dueño).
5. Tests en el mismo PR que el cambio. Nada de "lo testeo después".
6. Al terminar: `./scripts/verify.sh` en verde, tarea + tablero + `STATUS.md` actualizados en el PR, PR listo para revisión, traspaso y sesión nueva.
7. Protocolos completos: implementar → [docs/agents/implementer.md](docs/agents/implementer.md) · revisar → [reviewer.md](docs/agents/reviewer.md) · auditar (solo 🧠) → [auditor.md](docs/agents/auditor.md).

## Gestión de contexto

1. **Una tarea = una sesión.** Al cerrar una tarea, revisión o auditoría no sigas con otra: sugerí una sesión nueva (Claude Code: `/clear` o sesión nueva; 🤖: chat nuevo). Entre tareas, sesión nueva siempre es mejor que compactar.
2. **Sugerí compactar** solo a mitad de un trabajo largo y si: se cerró una sub-etapa (diagnóstico → implementación); se acumularon logs o archivos que ya no hacen falta; el contexto pasa ~60 % (🧠) o ~50 % (🤖); o hay señales de degradación (releer archivos, olvidar decisiones, repetir intentos fallidos).
3. **Antes, persistí:** "Notas de implementación" de la tarea (hecho / pendiente / próximo paso), commit y push.
4. **Cómo:**
   - 🧠: `/compact Conservar: tarea T-NNN, rama y PR, decisiones, archivos modificados, estado de verify y próximo paso. Descartar: logs y contenido de archivos ya procesados.` y «después escribime "seguí con T-NNN"».
   - 🤖 (no tiene `/compact`): «abrí un chat nuevo del agente de IntelliJ y escribí: Leé `AGENTS.md` y seguí `docs/agents/implementer.md` (retomando T-NNN)».
5. **No compactes** justo antes de terminar (terminá primero) ni entre tareas (sesión nueva).
6. **Higiene:** salida acotada (`-q`, `| tail -n 60`); grep/glob antes de abrir archivos y leé rangos de líneas; no abras `package-lock.json`, `target/`, `node_modules/`, `dist/`, `.verify-logs/` completos ni `docs/archive/`; no pegues archivos enteros en las respuestas; respuestas finales cortas.

## Configuración

- **Regla permanente:** todo archivo de configuración con valores por entorno tiene su **`.example` commiteado** y el archivo real en `.gitignore`. Los valores del ejemplo sirven tal cual para local y coinciden con `docker-compose.yml`; los secretos de ejemplo son inventados.
- Back: `cp server/env.properties.example server/src/main/resources/env.properties` (Spring lo importa del classpath). Front: sin variables hasta T-003 (`client/.env.example`).
- ¿Agregás o renombrás una variable? En el mismo PR: `.example`, `docker-compose.yml`, README § Configurar variables de entorno y la tabla de `docs/SETUP.md`.

## Mapa de documentación

| Archivo | Qué contiene | Cuándo leerlo |
|---|---|---|
| `docs/STATUS.md` | Dónde estamos, salud, próximo paso | Siempre, al empezar |
| `docs/BACKLOG.md` | Tablero y cola de tareas | Siempre, al empezar |
| `docs/tasks/T-NNN-*.md` | Spec de una tarea | Al tomar esa tarea |
| `docs/TESTING.md` | Estrategia y convenciones de tests | Al escribir tests |
| `CONTRIBUTING.md` | Ramas, commits, PR, revisión | Al abrir rama/PR o revisar |
| `docs/ARCHITECTURE.md` | Componentes, modelo, endpoints | Si la tarea toca estructura |
| `docs/SETUP.md` | Variables, sin Docker, IDE, agente de IntelliJ, problemas | Si el entorno falla |
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
- Título del PR = Conventional Commit (lo valida CI); un solo PR de tarea abierto; merge con squash, lo hace el dueño.
- Revisión siempre en una sesión distinta a la que implementó (o sub-agente, si el dueño lo pide).

## Límites: no tocar sin una tarea que lo pida

- Versiones mayores de dependencias, Java, Spring Boot, React, Node.
- Seguridad y autenticación (`server/.../config/security`, `config/jwt`, `CorsConfig`), secretos, `application.properties` de prod.
- Deploy (`server/Dockerfile`, `client/vercel.json`), CI (`.github/workflows/`), umbrales de cobertura (solo suben).
- Esquema de base de datos y datos seed (`config/DataInitializer.java`).
- Borrar, saltear (`@Disabled`, `.skip`) o debilitar tests. Commitear a `main`. Mergear sin revisión aprobada.

## Gotchas del entorno

- **JDK 21+ rompe la compilación** (Lombok 1.18.26): usá JDK 17 (`sdk env install`) hasta T-002. `verify.sh` lo avisa.
- `ddl-auto=create`: cada arranque del back **borra y recrea** el esquema y el seed vuelve a cargar los datos.
- El front llama a la API de **producción** (URL hardcodeada) hasta T-003, también en `--profile app`.
- Cualquier error no manejado del back devuelve **403 vacío** (`/error` protegido) hasta T-013: mirá el log del back.
- `ServerWakeUpScheduler` hace un GET a Render cada 14 min también en local y en tests (falla sin red: es ruido, no un error del test).
- Los `.sh` necesitan bash (Git Bash o WSL en Windows).
