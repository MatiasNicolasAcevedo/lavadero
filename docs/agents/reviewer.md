# Protocolo · Revisar un PR

**Lo usa:** el ejecutor que indica `Revisa:` en la tarea (matriz en `CONTRIBUTING.md` § 4), en una sesión **distinta** a la que implementó (🤖: chat nuevo) o como sub-agente del 🧠 (6b). Un PR por sesión.
GitHub: con `gh` o MCP si los tenés; **sin `gh`** (🤖): `git` + el dueño publica tu veredicto.

## 0. ¿Me corresponde?
- PR a revisar: el que te dijeron, o la línea "PR abierto" de `docs/STATUS.md` (con `gh`: `gh pr list --state open`).
- Abrí su tarea (`docs/tasks/T-NNN-*.md`) y mirá `Revisa:`. Si dice 🧠 y sos el 🤖, o 👤: **no revises**. Terminá con el traspaso (paso 7): «ejecuta: 🧠 · abrí una sesión nueva de Claude Code y escribí: Seguí `docs/agents/reviewer.md` para el PR #N».

## 1. Leer (en este orden, nada más)
- `AGENTS.md`, `CONTRIBUTING.md`, `docs/TESTING.md`.
- La descripción del PR (`gh pr view N`; sin `gh`: pedísela al dueño o leé la tarea).
- El archivo de tarea.
- El diff completo: `gh pr diff N` · sin `gh`: `git fetch origin <rama> && git diff origin/main...origin/<rama>`. Del código circundante, solo lo necesario.

## 2. Verificar vos mismo
- `gh pr checkout N` · sin `gh`: `git switch <rama> && git pull` (la rama es la de la tarea).
- `./scripts/verify.sh` → anotá el resumen. No confíes solo en el PR.
- CI: `gh pr checks N` (sin `gh`: la pestaña Checks del PR, o «sin dato» en el veredicto).
- Corré los comandos de los criterios de aceptación.

## 3. Revisar, en orden
1. **Criterios de aceptación:** uno por uno, con evidencia (test, comando o `archivo:línea`).
2. **Alcance y rama:** ¿tocó algo de "No incluye" o fuera de "Archivos previstos"? ¿La rama se llama **exactamente** como dice la tarea?
3. **Tests:** ¿cubren el cambio y el caso de error? ¿Fallarían si el código estuviera mal (rompé una línea si dudás)? ¿Borrados, `@Disabled`/`.skip`, asserts débiles? ¿Bajó la cobertura?
4. **Corrección:** bugs, casos borde, nulls, transacciones, manejo de errores.
5. **Seguridad:** secretos, validación de entrada, autorización, datos sensibles en logs.
6. **Convenciones:** las de `AGENTS.md`; nombres, duplicación, código muerto.
7. **Configuración:** variable nueva o cambiada → `.example`, `docker-compose.yml`, README y SETUP al día; sin secretos reales.
8. **Docs y registro:** tablero, tarea ("Notas", "Resultado"), `STATUS.md`, título Conventional Commit, tamaño (< ~400 líneas).

## 4. Publicar el veredicto
Con `gh`: `gh pr review N --approve|--request-changes --body-file <archivo>`; si es la misma cuenta que abrió el PR, `gh pr comment N --body-file <archivo>`. **Sin `gh`:** el texto completo va en tu respuesta y el dueño lo pega como comentario del PR. Formato:

```markdown
**Veredicto: APROBADO | CAMBIOS REQUERIDOS | ESCALAR A 🧠/DUEÑO**
Revisado por: <ejecutor> · verify.sh: <resumen> · CI: <estado>

### Criterios de aceptación
- ✅/❌ <criterio> — <evidencia>

### Bloqueantes
1. `archivo:línea` — problema · por qué importa · corrección sugerida

### No bloqueantes (opcionales, máx. 5)
- `archivo:línea` — sugerencia

### Fuera de alcance → Backlog
- <propuesta en una línea>
```

## 5. Reglas
- Sugerí, no reescribas el PR ni pushees a su rama. Nada fuera de alcance: va a "Fuera de alcance → Backlog".
- Cada bloqueante con `archivo:línea` y cómo comprobar el arreglo.
- **Segunda ronda:** solo lo pedido + regresiones. Si sigue sin aprobarse: `ESCALAR A 🧠/DUEÑO`.

## 6. Merge
- Lo hace el 👤 dueño (squash). No commitees a `main` ni al tablero.
- **Excepción (revisión con sub-agente):** solo si el dueño lo pidió explícitamente para **este** PR ("revisá y mergeá el PR #N"), con `APROBADO` y CI en verde (sin CI: `verify.sh` sin regresiones respecto de `main`), quien te lanzó hace squash merge y borra la rama.

## 6b. Modo sub-agente
Si el 🧠 te lanzó como sub-agente: trabajá solo con este archivo y el PR (no asumas nada de la sesión que te lanzó) y devolvé el veredicto con el formato del paso 4. Con `CAMBIOS REQUERIDOS`, el 🧠 corrige en la misma rama y te relanza (máx. 2 rondas; después escala al 👤).

## 7. Respuesta final (máx. 5 líneas) + traspaso
PR · veredicto · bloqueantes (cantidad y el principal). Cerrá con el formato de `AGENTS.md` § Traspasos:
- `APROBADO` → **ejecuta: 👤 dueño** · «GitHub → PR #N → Squash and merge y borrá la rama».
- `CAMBIOS REQUERIDOS` → el ejecutor de `Ejecuta:` de la tarea · «Seguí `docs/agents/implementer.md` (cambios pedidos en el PR #N)» en un chat nuevo del agente de IntelliJ (🤖) o una sesión nueva de Claude Code (🧠).
- `ESCALAR` → **ejecuta: 🧠** (o 👤) con la frase exacta.
