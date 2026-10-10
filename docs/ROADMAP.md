# Roadmap

Fases e hitos. El detalle de cada tarea está en [BACKLOG.md](BACKLOG.md). Al cerrar cada fase: release `v0.x.0` (ver CONTRIBUTING § 7).

## P0 · Estabilizar → `v0.1.0`

**Objetivo:** que cualquiera (persona o agente) lo levante y verifique en minutos, con una red de tests que permita cambiar sin miedo.

**Criterio de salida (verificable):**
- [ ] `./scripts/verify.sh` en verde con JDK 17 y 21, y CI en verde en `main`.
- [ ] Paso a paso del README seguido desde cero sin ⚠️ (front contra back local incluido).
- [ ] Secreto JWT fuera del repo y rotado; sin 🔴 abiertos en la auditoría.
- [ ] Infra de tests back (Testcontainers + JaCoCo con umbral), front (Vitest) y E2E (Playwright) funcionando.
- [ ] Flujos críticos 1–9 de la auditoría (§4.7) con al menos un test que falle si se rompen.
- [ ] Protección de `main` activa.

**Tareas:** T-001 … T-015.

## P1 · Actualizar → `v0.2.0`

**Objetivo:** dependencias vigentes y sin vulnerabilidades conocidas, en pasos chicos protegidos por la red de P0.

**Criterio de salida:** Java 21, Spring Boot 4.x, Node 24, React 19, Vite vigente, ESLint 9, NextUI → HeroUI; `npm audit` sin altas/críticas; Dependabot activo y al día; `verify.sh` + E2E en verde en cada paso.

**Tareas principales:** ver [ADR 0002](adr/0002-spring-boot-upgrade-path.md) y la sección P1 del Backlog.

## P2 · Terminar → `v0.3.0`

**Objetivo:** completar la funcionalidad a medias, cada feature con su spec en `docs/specs/` y sus tests.

**Criterio de salida:** búsqueda paginada de clientes, edición/baja de entidades, agenda de turnos con transiciones validadas, listado de cobros; perfiles dev/prod y migraciones con Flyway; errores con `ProblemDetail`.

## P3 · Pulir para portfolio → `v1.0.0`

**Objetivo:** que el repo se luzca.

**Criterio de salida:** responsive y accesibilidad revisados con E2E en mobile; cobertura ≥ 70 % en servicios y lógica de front; README con capturas/GIF propios, badges de CI y cobertura, licencia, diagrama; demo online estable; release `v1.0.0`.
