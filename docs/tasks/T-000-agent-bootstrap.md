# T-000 · Auditoría inicial y harness para agentes
- Estado: Hecha · Prioridad: P0 · Esfuerzo: L · Ejecuta: 🧠 modelo de frontera
- Fase: P0 Estabilizar
- Rama: `claude/new-session-cj7dg4` (impuesta por el entorno de la sesión) · Depende de: — · Spec/ADR: [0001](../adr/0001-testcontainers-postgres-over-h2.md), [0002](../adr/0002-spring-boot-upgrade-path.md)
- Revisa: 🧠 modelo de frontera (en la misma sesión, a pedido del dueño) + 👤 dueño

## Por qué
Proyecto de 2025 sin docs para retomarlo, sin gate de verificación ni CI, y con un README que no permite levantarlo.

## Objetivo
Auditoría con evidencia, harness (`verify.sh`, `dev.sh`, toolchain, `.env`), README verificado, docs vivos, tablero P0 y protocolos para agentes. Solo docs y harness (modo A): sin cambios de código de la app.

## Criterios de aceptación
- [x] `docs/audits/2026-10-10-initial.md` con hallazgos por dimensión y tareas
- [x] `scripts/verify.sh` y `scripts/dev.sh` ejecutados en esta sesión
- [x] README paso a paso probado desde un clon limpio
- [x] Tablero con 10 tareas en Listas (5 🤖) y protocolos en `docs/agents/`

## Resultado
- PR: #33 · Qué cambió: ver descripción del PR · Tests agregados: — · Pendientes → T-001…T-015 y Backlog
