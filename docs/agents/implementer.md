# Protocolo · Implementar una tarea

Sos el agente implementador. Una tarea por sesión. Seguí estos pasos en orden; no te saltees ninguno. GitHub: usá `gh` (o el MCP de GitHub si es lo que tenés; sin ninguno, `git ls-remote --heads origin` y el PR desde la web).

## 1. Orientarte (sin escribir código)
- Leé `AGENTS.md`, `docs/STATUS.md` y `docs/BACKLOG.md`. Nada más por ahora.
- `git status` · `git log --oneline -10` · `gh pr list --state open`.

## 2. Primero lo que está en vuelo
- Si un PR tuyo/de agentes tiene **cambios pedidos** (`gh pr view N --comments`, veredicto `CAMBIOS REQUERIDOS`): `git switch <su rama>` y atendé solo eso (paso 6 → paso 10). No tomes otra tarea.
- Si ya hay **2 PR esperando revisión**: no tomes nada. Avisá al usuario («hay 2 PR esperando revisión: #A, #B; revisalos con `docs/agents/reviewer.md`») y terminá.

## 3. Línea de base
- `git switch main && git pull`
- `./scripts/verify.sh` → anotá el resumen. Si falla en algo que tu tarea no arregla, no empieces nada nuevo: tomá la tarea que lo arregla (o creala en Backlog y avisá).

## 4. Tomar la tarea
- La **primera** de "✅ Listas" con `🤖 ligero`, dependencias hechas y sin PR ni rama remota (`git ls-remote --heads origin | grep T-NNN`).
- `git switch -c <rama exacta de la tarea>`
- En `docs/BACKLOG.md` mové la línea a "🔨 En curso" (sin número de PR todavía) y en el archivo de tarea poné `Estado: En curso`.
- `git commit -am "docs(board): start T-NNN" ` → `git push -u origin <rama>`
- `gh pr create --draft --title "<tipo>(<alcance>): <título de la tarea en inglés>" --body-file .github/pull_request_template.md` y completá "Tarea". Agregá `· PR #N` a la línea del tablero en el próximo commit.

## 5. Contexto mínimo
- Leé el archivo de la tarea y **solo** lo que digan "Leer antes" y "Archivos previstos", por rangos de líneas.
- No leas `docs/archive/`, `docs/audits/`, lockfiles, `target/`, `node_modules/` ni `dist/` salvo que la tarea lo pida.

## 6. Implementar con tests
- Respetá "Alcance" y sobre todo "**No incluye**".
- Bugfix: primero el test que reproduce el bug y falla (commit propio), después el fix.
- Escribí los tests de la sección "Tests" copiando el estilo del archivo de referencia.
- Entre pasos corré el test más específico (`./mvnw -q test -Dtest='X#y'`); al final `./scripts/verify.sh`.
- Commits atómicos: `tipo(alcance): descripción` + línea en blanco + `Refs: T-NNN`.

## 7. Contexto largo
Si la sesión se alarga (ver `AGENTS.md` § Gestión de contexto): completá "Notas de implementación" (hecho / pendiente / próximo paso), commit y push, y decile al usuario:
`/compact Conservar: tarea T-NNN, rama y PR, decisiones, archivos modificados, estado de verify y próximo paso. Descartar: logs y contenido de archivos ya procesados.` → «después escribime "seguí con T-NNN"».

## 8. Reglas duras
- No edites criterios de aceptación para que "pasen". No borres, saltees (`@Disabled`, `.skip`) ni debilites tests. No bajes umbrales de cobertura.
- No cambies versiones mayores, arquitectura, seguridad, CI ni deploy si la tarea no lo pide. No toques secretos.
- No commitees a `main`. No mergees. Trabajo extra que descubras → una línea nueva en "📥 Backlog", no lo hagas "de paso".

## 9. Escalar en vez de insistir
Si fallaste **2 veces** en lo mismo, necesitás una decisión de diseño, o tenés que tocar algo fuera del alcance:
- Commiteá lo sano (o revertí lo roto). En "Notas de implementación": qué probaste, qué falló, qué falta decidir.
- Mové la tarea a "⛔ Bloqueadas" con `bloqueada por: requiere 🧠 frontera` o `requiere: dueño` y el motivo.
- Push, dejá el PR en draft con un comentario (`gh pr comment N --body "..."`) y terminá.

## 10. Cerrar
- Marcá cada criterio de aceptación `[x]` solo si lo comprobaste.
- Completá "Notas de implementación" y "Resultado" de la tarea; `Estado: En revisión`.
- `docs/BACKLOG.md`: mové la línea a "🏁 Hecho reciente" como `- T-NNN · <título> · AAAA-MM-DD · PR #N` (así `main` refleja el estado real al mergear). Si el PR se cierra sin mergear, el siguiente PR que toque el tablero la devuelve a "Listas"/"Bloqueadas". Si "Hecho reciente" pasa de 10, mové las más viejas (y sus archivos de tarea) a `docs/archive/tasks/`.
- `docs/STATUS.md`: fecha, modelo, salud (verify, cobertura), **próximo paso exacto**, línea en "Últimas sesiones" (máx. 5; las viejas → `docs/archive/log/AAAA-MM.md`).
- Push. Completá la descripción del PR (resumen de `verify.sh`, tests agregados, capturas 375 px + 1280 px si hay UI, riesgos) y `gh pr ready N`. Indicá quién revisa según la matriz de `CONTRIBUTING.md` § 4.

## 11. Respuesta final (máx. 5 líneas)
Tarea y PR · resultado de `verify.sh` y cobertura · quién revisa · qué sigue. Cerrá con:
«Para revisar, abrí una sesión nueva y escribí: Seguí `docs/agents/reviewer.md` para el PR #N».
