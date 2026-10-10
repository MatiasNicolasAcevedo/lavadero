# Protocolo · Auditoría periódica (agente de frontera)

Cuándo: cada 5–10 PR mergeados, antes de un cambio grande (migración de versión, nueva fase) o con 2+ tareas bloqueadas por `requiere 🧠`. Trabajá por bloques; al terminar cada uno, persistí y commiteá en la rama `docs/audit-AAAA-MM-DD`. Si el contexto está cargado entre bloques, decile al usuario:
`/compact Conservar: auditoría AAAA-MM-DD, rama y PR, bloque N terminado, hallazgos ya volcados al archivo y próximo bloque. Descartar: salidas de comandos y archivos leídos.` → «después escribime "seguí la auditoría desde el bloque N"».

## Bloque A · Situación
1. Leé `AGENTS.md`, `docs/STATUS.md`, `docs/BACKLOG.md`, `docs/ROADMAP.md`, `docs/TESTING.md` y la última auditoría de `docs/audits/`.
2. Qué cambió: `git log --oneline audit/<fecha anterior>..origin/main` y `git diff --stat audit/<fecha anterior>..origin/main` (la primera vez, desde `2026-10-10`); PR mergeados y sus revisiones (`gh pr list --state merged --limit 20`, `gh pr view N --comments`).
3. Creá `docs/audits/AAAA-MM-DD-periodic.md` con el resumen. Commit.

## Bloque B · Trabajo de los agentes
4. Calidad de lo mergeado: ¿los criterios se cumplieron de verdad? ¿Hubo cambios fuera de alcance? ¿Las revisiones dejaron pasar problemas?
5. Tests significativos: spot check rompiendo a propósito una línea de lógica en 2–3 servicios mergeados y viendo si algún test falla (revertir después); o PIT/Stryker puntual.
6. Cada problema → tarea nueva (archivo si es P0/P1, línea en Backlog si no). Commit.

## Bloque C · Salud
7. Cobertura: medila, compará con el umbral; si creció, subí el umbral (redondeando hacia abajo) en el mismo PR. Tests lentos o inestables → tareas.
8. Onboarding: seguí el paso a paso del README desde un clon limpio (`git clone` en una carpeta temporal, sin `.env` ni dependencias). Corregí lo que falle o esté desactualizado y actualizá la línea "Verificado el …".
9. Deriva documental: `README`, `AGENTS.md`, `CONTRIBUTING.md`, `TESTING.md`, `ARCHITECTURE.md`, `SETUP.md` y los comandos siguen siendo ciertos; límites de tamaño respetados. Commit.

## Bloque D · Resolver y replanificar
10. Decisiones pendientes del dueño: si ya respondió, escribí el ADR y desbloqueá tareas.
11. Tareas `🧠` prioritarias: cada una en su rama y PR, idealmente en su propia sesión (no en la de la auditoría si el contexto ya está cargado).
12. Relevamiento liviano de las 14 dimensiones de la auditoría inicial, **solo** en lo que cambió.
13. Replanificar: repriorizar; refinar Backlog → Listas hasta 5–10 tareas (mayoría 🤖, cumpliendo la Definición de Listo); partir tareas grandes; actualizar `ROADMAP.md`; si se cerró una fase, proponer release (`CONTRIBUTING.md` § 7).
14. Ajustar el sistema: si el agente ligero o el revisor fallaron repetidamente en algo, o las sesiones compactan seguido (tareas demasiado grandes), mejorar `AGENTS.md`, plantillas o protocolos sin pasar sus límites de tamaño. Commit.

## Cierre
15. `docs/STATUS.md` actualizado (salud, fase, próximo paso exacto, "Últimas sesiones"); archivar lo que exceda los límites (`docs/archive/`).
16. PR `docs: periodic audit AAAA-MM-DD`. Después del merge, el dueño crea el tag: `git tag audit/AAAA-MM-DD origin/main && git push origin audit/AAAA-MM-DD`.
17. Respuesta final (máx. 8 líneas): estado, top 5 hallazgos, tareas nuevas, decisiones para el dueño, PR. Sugerí sesión nueva: «Seguí `docs/agents/implementer.md`».
