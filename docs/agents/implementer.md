# Protocolo · Implementar una tarea

**Lo usan:** 🤖 agente gratuito (IntelliJ) y 🧠 modelo de frontera. Una tarea por sesión, siempre **la primera de la cola**. Seguí los pasos en orden.
GitHub: el 🧠 usa `gh` o el MCP de GitHub; el 🤖 **no tiene ninguno**: solo `git` en la terminal del IDE (alternativas marcadas "sin `gh`").

## 1. Orientarte (sin escribir código)
- Leé `AGENTS.md`, `docs/STATUS.md` y `docs/BACKLOG.md`. Nada más por ahora.
- `git status` · `git log --oneline -10` · PR abiertos: `gh pr list --state open` (sin `gh`: `git fetch origin && git ls-remote --heads origin | grep T-` y la línea "PR abierto" de `STATUS.md`).

## 2. Primero lo que está en vuelo (máx. 1 PR de tarea abierto)
- Si el PR abierto tiene **cambios pedidos** (veredicto `CAMBIOS REQUERIDOS`, en el PR o en "Notas" de la tarea) y su tarea es para tu ejecutor: `git switch <su rama> && git pull`, atendé solo eso (paso 6 → paso 10) y terminá.
- Si hay un PR de tarea abierto **esperando revisión o merge**: no tomes nada. Terminá con el traspaso de la revisión (paso 11) o «👤 dueño: mergeá el PR #N».

## 3. Línea de base
- `git switch main && git pull` · `./scripts/verify.sh` → anotá el resumen.
- Si falla en algo que tu tarea no arregla, no empieces: la tarea que lo arregla tiene que ir primera en la cola (si no existe, agregala a Backlog y traspasá a 🧠).

## 4. Tomar la tarea
- **La primera** de "✅ Listas". No mires las demás. Excepción: si en "⛔ Bloqueadas" hay una tarea **escalada** (`requiere 🧠` / `requiere 👤`, paso 9), la cola está detenida y esa es la primera.
- **¿Te corresponde?** Mirá `Ejecuta:` en la tarea. Si es 🧠 y sos el 🤖, o es 👤 (y no dice "no bloquea la cola"): **no la tomes ni saltees a la siguiente**. Terminá con el traspaso (paso 11). Una 👤 "(no bloquea la cola)" se saltea.
- Rama **antes de cualquier commit**, con el nombre exacto de la tarea: `git switch -c <tipo>/T-NNN-slug`.
- `docs/BACKLOG.md`: línea a "🔨 En curso"; en la tarea `Estado: En curso`. `git commit -am "docs(board): start T-NNN" -m "Refs: T-NNN"` → `git push -u origin <rama>`.
- PR en draft, título Conventional Commit en inglés, cuerpo = `.github/pull_request_template.md` con "Tarea" completa:
  - Con `gh`: `gh pr create --draft --title "<tipo>(<alcance>): <título>" --body-file <cuerpo>`.
  - **Sin `gh` (🤖):** no lo abras vos. Dejale al dueño en tu respuesta: el link `https://github.com/MatiasNicolasAcevedo/lavadero/compare/main...<rama>?expand=1`, el **título** y el **cuerpo** listos para pegar, y «abrilo como draft (Create draft pull request)». Seguí trabajando; el número del PR lo anota el próximo commit que toque el tablero.

## 5. Contexto mínimo
- Leé la tarea y **solo** "Leer antes" y "Archivos previstos", por rangos de líneas.
- No leas `docs/archive/`, `docs/audits/`, lockfiles, `target/`, `node_modules/` ni `dist/`.

## 6. Implementar con tests
- Respetá "Alcance" y sobre todo "**No incluye**".
- Bugfix: primero el test que reproduce el bug y falla (commit propio), después el fix.
- Tests de la sección "Tests", copiando el estilo del archivo de referencia.
- Entre pasos, el test más específico (`cd server && ./mvnw -q test -Dtest='X#y'`); al final `./scripts/verify.sh`.
- Si agregás o cambiás una variable de configuración: su `.example`, `docker-compose.yml`, README y `docs/SETUP.md` en el mismo PR.
- Commits atómicos: `tipo(alcance): descripción` + línea en blanco + `Refs: T-NNN`. `git push` después de cada commit.

## 7. Contexto largo
Si la sesión se alarga (`AGENTS.md` § Gestión de contexto): completá "Notas de implementación" (hecho / pendiente / próximo paso), commit y push, y decile al dueño:
- 🧠: `/compact Conservar: tarea T-NNN, rama y PR, decisiones, archivos modificados, estado de verify y próximo paso. Descartar: logs y contenido de archivos ya procesados.` → «después escribime "seguí con T-NNN"».
- 🤖 (sin `/compact`): «Abrí un chat nuevo del agente de IntelliJ y escribí: Leé `AGENTS.md` y seguí `docs/agents/implementer.md` (retomando T-NNN)».

## 8. Reglas duras
- No edites criterios de aceptación. No borres, saltees (`@Disabled`, `.skip`) ni debilites tests. No bajes umbrales.
- No cambies versiones mayores, arquitectura, seguridad, CI, deploy ni configuración de prod sin tarea. No toques secretos.
- No commitees a `main`. No mergees. Trabajo extra → una línea en "📥 Backlog", no "de paso".

## 9. Escalar en vez de insistir
Tras **2 intentos fallidos** en lo mismo, una decisión de diseño o algo fuera de alcance:
- Commiteá lo sano (o revertí lo roto). "Notas de implementación": qué probaste, qué falló, qué falta decidir.
- Tarea a "⛔ Bloqueadas" **primera de la lista**, con `bloqueada por: requiere 🧠` o `requiere 👤` y el motivo. **La cola queda detenida en esa tarea:** nadie toma la siguiente hasta resolverla.
- Push. Comentario en el PR draft (`gh pr comment N`; sin `gh`, el texto va en tu respuesta para que el dueño lo pegue).
- Terminá con el traspaso (paso 11), p. ej. «ejecuta: 🧠 · abrí una sesión nueva de Claude Code y escribí: Seguí `docs/agents/implementer.md` para retomar T-NNN».

## 10. Cerrar
- Criterios `[x]` solo si los comprobaste. "Notas" y "Resultado"; `Estado: En revisión`.
- `docs/BACKLOG.md`: línea a "🏁 Hecho reciente" (`- T-NNN · <título> · AAAA-MM-DD · PR #N`). Si pasa de 10, archivá las más viejas en `docs/archive/tasks/`.
- `docs/STATUS.md`: fecha, ejecutor, salud, "PR abierto", **Próximo paso / Qué hacer / Después** (la revisión de este PR) y "Últimas sesiones" (máx. 5).
- Push. Descripción del PR completa (resumen de `verify.sh`, tests, capturas 375/1280 px si hay UI, riesgos) y ready: `gh pr ready N` (sin `gh`: pedíselo al dueño en el traspaso). Quién revisa: "Revisa" de la tarea.

## 11. Respuesta final (máx. 5 líneas) + traspaso
Tarea y PR (o link de comparación) · `verify.sh` · qué sigue. Cerrá con el formato de `AGENTS.md` § Traspasos, p. ej.:
> **Próximo paso:** revisar PR #N (T-NNN) · **ejecuta: 🤖 agente gratuito (IntelliJ)**
> **Qué hacer:** abrí un chat nuevo del agente de IntelliJ y escribí: «Leé `AGENTS.md` y seguí `docs/agents/reviewer.md` para el PR #N»

(Si revisa 🧠: «abrí una sesión nueva de Claude Code y escribí: Seguí `docs/agents/reviewer.md` para el PR #N».)
