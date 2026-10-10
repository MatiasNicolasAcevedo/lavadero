# Protocolo · Revisar un PR

Sos el agente revisor, en una sesión **distinta** a la que implementó y sin su contexto. Un PR por sesión. Si no te dijeron el número: `gh pr list --state open` y tomá el más viejo que no sea draft y no tenga tu revisión.

## 1. Leer (en este orden, nada más)
- `AGENTS.md`, `CONTRIBUTING.md`, `docs/TESTING.md`.
- La descripción del PR: `gh pr view N`.
- El archivo de tarea que enlaza (`docs/tasks/T-NNN-*.md`).
- El diff completo: `gh pr diff N`. Del código circundante, solo lo necesario para entender el cambio.

## 2. Verificar vos mismo
- `gh pr checkout N` (o `git fetch origin <rama> && git switch <rama>`).
- `./scripts/verify.sh` → anotá el resumen. No confíes solo en lo que dice el PR.
- Estado de CI: `gh pr checks N`.
- Si la tarea tiene criterios con comandos concretos, correlos.

## 3. Revisar, en orden
1. **Criterios de aceptación:** uno por uno, ¿se cumplen de verdad? ¿Hay un test o un comando que lo demuestre?
2. **Alcance:** ¿tocó algo de "No incluye" o fuera de "Archivos previstos"? ¿Hay cambios sin explicar?
3. **Tests:** ¿cubren el cambio y el caso de error? ¿Fallarían si el código estuviera mal (probá romper una línea si dudás)? ¿Hay tests borrados, `@Disabled`/`.skip`, asserts debilitados o tests sin asserts? ¿Bajó la cobertura o el umbral?
4. **Corrección:** bugs, casos borde, nulls, transacciones, manejo de errores, concurrencia.
5. **Seguridad:** secretos, validación de entrada, autorización, datos sensibles en logs.
6. **Convenciones:** las de `AGENTS.md`; nombres, duplicación, código muerto.
7. **Docs y registro:** README/SETUP si cambió cómo se levanta; tablero, tarea ("Notas", "Resultado") y `STATUS.md` coherentes; título del PR en Conventional Commit; tamaño razonable (< ~400 líneas).

## 4. Publicar la revisión
Con `gh pr review N --approve|--request-changes --body-file <archivo>` si la cuenta lo permite; si es la misma cuenta que abrió el PR, `gh pr comment N --body-file <archivo>`. Formato:

```markdown
**Veredicto: APROBADO | CAMBIOS REQUERIDOS | ESCALAR A 🧠/DUEÑO**
Revisado por: <modelo/agente> · verify.sh: <resumen> · CI: <estado>

### Criterios de aceptación
- ✅/❌ <criterio> — <evidencia: test, comando o archivo:línea>

### Bloqueantes
1. `archivo:línea` — problema · por qué importa · corrección sugerida

### No bloqueantes (opcionales, máx. 5)
- `archivo:línea` — sugerencia

### Fuera de alcance → Backlog
- <propuesta en una línea>
```

## 5. Reglas
- Sugerí, no reescribas el PR ni pushees a su rama.
- No pidas cambios fuera del alcance de la tarea: proponelos en "Fuera de alcance → Backlog".
- Sé concreto y verificable: cada bloqueante con `archivo:línea` y cómo comprobar el arreglo.
- **Segunda ronda:** revisá solo lo pedido + regresiones. Si sigue sin poder aprobarse, veredicto `ESCALAR A 🧠/DUEÑO`.
- Tarea 🧠 o que toca seguridad, datos o CI: aunque apruebes, el merge lo decide el dueño.

## 6. Después del veredicto
- La política del proyecto es que **mergea el dueño**: si es `APROBADO`, dejalo listo y avisá. No mergees.
- No commitees a `main` ni al tablero: la línea en "Últimas sesiones" de `STATUS.md` la agrega el próximo PR que toque el tablero.

## 7. Respuesta final (máx. 5 líneas)
PR · veredicto · bloqueantes (cantidad y el más importante) · próximo paso (merge del dueño, ronda 2 o escalado). Sugerí sesión nueva para lo que siga: «Seguí `docs/agents/implementer.md`».
