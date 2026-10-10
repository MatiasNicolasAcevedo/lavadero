# T-016 · Alinear el harness con el prompt de bootstrap v3
- Estado: En curso · Prioridad: P0 · Esfuerzo: M · Ejecuta: 🧠 modelo de frontera
- Fase: P0 Estabilizar
- Rama: `chore/T-016-align-harness-v3` (crearla desde `main` actualizado antes del primer commit) · Depende de: T-000 · Spec/ADR: —
- Revisa: 🧠 modelo de frontera (sesión nueva) + 👤 dueño

## Por qué
El bootstrap (T-000, PR #33, `a5a6d73`) se hizo con una versión anterior del prompt. La v3 cambia el ejecutor 🤖 (agente gratuito de IntelliJ con Qwen, sin `gh` ni `/compact`), pide una cola secuencial con 1 PR abierto, traspasos explícitos, revisión con sub-agente, la regla de `.example` y todo en contenedores.

## Objetivo
`main` refleja la v3: cualquier ejecutor sabe qué tarea toca, si le corresponde y qué decirle al dueño cuando no; el proyecto levanta con Docker como único requisito.

## Alcance
- Incluye: docs, protocolos, plantillas, tareas (solo formato v3 y criterios nuevos), README/SETUP, compose en la raíz, `Dockerfile.dev`/`.dockerignore`, scripts.
- **No incluye:** código de la app, versiones, configuración de producción (`server/Dockerfile`, `application.properties`, `vercel.json`), CI, reescribir hallazgos de la auditoría o tareas más allá del formato.

## Criterios de aceptación
PC-A (docs y protocolos)
- [ ] 1 · Ejecutores: 🤖 = agente gratuito de IntelliJ (Qwen), sin `gh`/MCP/`/compact` en `AGENTS.md`, `CONTRIBUTING.md`, `SETUP.md`, `BACKLOG.md`, protocolos, plantilla y las 16 tareas (`Ejecuta:` y `Revisa:` con ícono); auditoría: solo supuestos §5
- [ ] 2 · Tablero: reglas v3 (máx. 1 PR, cola en orden, no saltear), "Listas" = cola en orden de ejecución, "Bloqueadas" en orden de entrada
- [ ] 3 · Traspasos: formato en `AGENTS.md`, `STATUS.md` y cierre de los 3 protocolos; `reviewer.md` paso 0; `auditor.md` solo 🧠; `implementer.md` toma siempre la primera, se detiene si no le corresponde, link de comparación sin `gh`, cola detenida al escalar
- [ ] 4 · Revisión con sub-agente en `CONTRIBUTING.md` (matriz con "Quién lanza la revisión" y excepción de merge) y en `reviewer.md` (6 y 6b)
- [ ] 5 · Archivo de instrucciones del agente de IntelliJ verificado (o `⚠️ no verificado` + frase de arranque); `QWEN.md` decidido; `SETUP.md` con la sección del agente
- [ ] 8 · Auditoría: sección "Contexto inferido" con evidencia y supuestos del agente corregidos
- [ ] 9 · `STATUS.md` en formato v3 (PR abierto, Próximo paso, Qué hacer, Después)

PC-B (configuración y contenedores)
- [ ] 6 · Regla de `.example` en `AGENTS.md`, `CONTRIBUTING.md` (punto 9) y template de PR; README "Configurar variables de entorno"; `SETUP.md` con "dónde se define en prod"; T-003 pide `client/.env.example`
- [ ] 7 · `docker-compose.yml` en la raíz (`db` sin profile; `server` y `client` con profile `app`), `client/Dockerfile.dev`, `.dockerignore` de back y front; scripts y docs actualizados; destino de `server/docker-compose.yml` documentado; T-003 con el criterio del front contra el back del compose
- [ ] Los dos modos verificados desde un `git clone` nuevo de la rama (health, front, login)
- [ ] 10 · Tag `audit/2026-10-10` y borrado de `claude/new-session-cj7dg4`, o 👤 en `STATUS.md`

Cierre
- [ ] Coherencia de links, IDs, ramas y ejecutores (script de links)
- [ ] Prueba en seco de `implementer.md` con la primera 🤖 y con una 🧠 primera
- [ ] T-016 en "Hecho reciente" y PR en ready

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: — · Pendientes → nuevas tareas: …
