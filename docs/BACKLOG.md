# Tablero
> Reglas: máx. 2 PR abiertos a la vez · una tarea con PR abierto o rama remota `*/T-NNN-*` está tomada ·
> tomar la primera de "Listas" cuyo agente coincida y cuyas dependencias estén hechas ·
> formato: `- T-NNN · Pn · S/M/L · 🤖 ligero|🧠 frontera|👤 dueño · Título → [tarea](tasks/T-NNN-slug.md)`
> Próximo ID libre: T-016

## 🔨 En curso / en revisión (con su PR)
- (vacío)

## ✅ Listas (ordenadas por prioridad, máx. 10)
- T-001 · P0 · S · 🤖 ligero · Hacer que el lint del front pase sin warnings → [tarea](tasks/T-001-fix-client-lint-warnings.md)
- T-005 · P0 · S · 🧠 frontera · Sacar el secreto JWT del repo y rotarlo → [tarea](tasks/T-005-remove-committed-jwt-secret.md)
- T-010 · P0 · S · 👤 dueño · Proteger `main` y limpiar ramas → [tarea](tasks/T-010-protect-main-branch.md)
- T-003 · P0 · S · 🤖 ligero · Configurar la URL de la API del front por entorno → [tarea](tasks/T-003-configure-api-base-url.md)
- T-002 · P0 · S · 🤖 ligero · Actualizar Lombok para compilar con JDK 21 → [tarea](tasks/T-002-upgrade-lombok-for-jdk21.md)
- T-004 · P0 · S · 🤖 ligero · Quitar dependencias sin uso del front → [tarea](tasks/T-004-remove-unused-client-deps.md)
- T-006 · P0 · M · 🧠 frontera · Infraestructura de tests del back: Testcontainers + JaCoCo → [tarea](tasks/T-006-backend-test-infrastructure.md)
- T-008 · P0 · S · 🤖 ligero · Tests de caracterización de login y registro → [tarea](tasks/T-008-characterize-auth-services.md)
- T-009 · P0 · S · 🤖 ligero · Completar los tests de TurnoServiceImpl → [tarea](tasks/T-009-complete-turno-service-tests.md)
- T-007 · P0 · M · 🧠 frontera · Infraestructura de tests del front: Vitest + RTL + MSW → [tarea](tasks/T-007-frontend-test-infrastructure.md)

## ⛔ Bloqueadas
- T-011 · P0 · S · 🧠 frontera · Agregar CI que corra el gate → [tarea](tasks/T-011-add-ci-workflow.md) · bloqueada por: T-001
- T-012 · P0 · M · 🧠 frontera · Infraestructura E2E con Playwright → [tarea](tasks/T-012-e2e-smoke-infrastructure.md) · bloqueada por: T-003, T-011
- T-013 · P0 · M · 🧠 frontera · Respuestas de error y autenticación correctas → [tarea](tasks/T-013-fix-auth-error-responses.md) · bloqueada por: T-006, T-008
- T-014 · P0 · M · 🧠 frontera · Separar configuración dev/prod → [tarea](tasks/T-014-split-dev-prod-profiles.md) · bloqueada por: decisión del dueño (resetear o persistir la demo), T-006
- T-015 · P0 · S · 🤖 ligero · Activar Dependabot → [tarea](tasks/T-015-enable-dependabot.md) · bloqueada por: T-011

## 📥 Backlog (sin refinar: una línea, sin archivo de tarea todavía)
P0 (después de lo anterior)
- Tests `@WebMvcTest` por controller (clientes, vehículos, turnos, cobros) copiando el de T-013 · P0 · 🤖
- Filas nuevas de "Problemas frecuentes" del README con lo que vayan encontrando los agentes · P0 · 🤖
P1 Actualizar
- Java 21 (pom, Dockerfile a `eclipse-temurin`, `.sdkmanrc`, CI) · P1 · 🧠 · ADR 0002
- Spring Boot 3.3 → 3.5 con OpenRewrite, springdoc 2.8 · P1 · 🧠 · ADR 0002
- Spring Boot 3.5 → 4.x, Security 7, jjwt 0.13, springdoc 3 · P1 · 🧠 · ADR 0002
- Quitar `mysql-connector-j`, `hibernate-types-60`, versiones fijadas que gestiona el BOM · P1 · 🤖
- Node 24 LTS (`.nvmrc`, CI) · P1 · 🤖
- React 19 + `createRoot` · P1 · 🧠
- Vite 8, ESLint 9 (flat config), TypeScript al día · P1 · 🧠
- NextUI (deprecado) → HeroUI y Tailwind 4 · P1 · 🧠
- `npm audit` sin altas: updates menores restantes · P1 · 🤖
P2 Terminar
- Búsqueda paginada de clientes: rescatar `feat/add-client-search-paged`, validar `sortBy`, tests, consumir en el front · P2 · 🤖 (con spec)
- Transiciones de turno válidas (PENDIENTE→EN_PROCESO→FINALIZADO) y estado inicial fijo · P2 · 🧠 spec
- Edición y baja de clientes, vehículos y turnos · P2 · 🧠 spec
- Listado de cobros / caja diaria · P2 · 🧠 spec
- Agenda de turnos (todos, por fecha) · P2 · 🧠 spec
- Flyway + `ddl-auto=validate` · P2 · 🧠
- Errores con `ProblemDetail` · P2 · 🧠
- `@Transactional` en servicios y `Clock` inyectado para fechas · P2 · 🤖
- N+1 en `GET /clientes` (vehículos embebidos) y cobro inverso · P2 · 🧠
- Capa de API única en el front + cerrar sesión ante 401 · P2 · 🧠
- Estados loading / error / vacío consistentes en pantallas · P2 · 🤖
- `@Builder.Default` en listas de entidades; `@ToString` de `User` sin password · P2 · 🤖
- CORS restringido por entorno; revisar JWT de 10 días en `localStorage` · P2 · 🧠
- `server/.dockerignore` (no copiar `env.properties`, `target/`) · P2 · 🤖
- Quitar `DemoController`, `client/netlify.toml`; renombrar paquete `coworkproject`; logger en el scheduler · P2 · 🤖
P3 Pulir
- Accesibilidad: label en la búsqueda, contraste de botones azules/verdes, `<main>`, `<h1>` en la home · P3 · 🤖
- Formato de enums y montos en pantalla · P3 · 🤖
- README con capturas/GIF propios, badges de cobertura, LICENSE (decisión del dueño), diagrama · P3 · 🤖
- Partir `Turnos.tsx` (412 líneas) y `ClientVehicles.tsx` (323) en componentes · P3 · 🧠
- Spotless/Prettier (decisión de estilo) · P3 · 🧠
- Mutation testing puntual (PIT / Stryker) · P3 · 🧠

## 🏁 Hecho reciente (máx. 10; las más viejas → archive/tasks/)
- T-000 · Auditoría inicial y harness para agentes · 2026-10-10 · PR #33
