# T-NNN · <Título en imperativo>
- Estado: Lista · Prioridad: P0–P3 · Esfuerzo: S/M/L · Ejecuta: 🤖 agente gratuito | 🧠 modelo de frontera | 👤 dueño
- Fase: <P0 Estabilizar | P1 Actualizar | P2 Terminar | P3 Pulir>
- Rama: `<tipo>/T-NNN-slug` (crearla desde `main` actualizado antes del primer commit) · Depende de: T-NNN · Spec/ADR: <links o "—">
- Revisa: <🤖 agente gratuito (chat nuevo) | 🧠 modelo de frontera | 👤 dueño> (según la matriz de CONTRIBUTING § 4)

## Por qué
<2–4 líneas: problema y evidencia (archivo:línea o hallazgo de la auditoría)>

## Objetivo
<resultado observable en 1–2 líneas>

## Alcance
- Incluye: ...
- **No incluye:** ... (lo que NO se debe tocar aunque parezca relacionado)

## Leer antes (máx. 5 referencias, con sección o rango de líneas)
- `server/src/.../XService.java` (método `y`, ~L40–90)
- docs/TESTING.md § 3 · ejemplo a copiar: `.../XServiceImplTest.java`

## Archivos previstos
- modificar: ... · crear: ...

## Tests
- Agregar: <qué tests, de qué tipo, qué casos (incluido el de error)>
- Si es bugfix: test que reproduce el bug y falla antes del fix.

## Pasos sugeridos
1. ...

## Criterios de aceptación (verificables, de a uno)
- [ ] `./scripts/verify.sh` pasa y la cobertura no baja
- [ ] <comando o prueba concreta con resultado esperado>
- [ ] <comportamiento observable cubierto por `XTest#metodo`>
- [ ] Si cambió la configuración o cómo se levanta: `.example`, compose, README y SETUP actualizados y probados

## Notas de implementación  _(las completa quien implementa; también antes de compactar)_
- Hecho: ... · Pendiente: ... · Próximo paso: ...

## Resultado  _(al cerrar)_
- PR: #N · Qué cambió: ... · Tests agregados: ... · Pendientes → nuevas tareas: T-NNN

<!--
Reglas para quien escribe la tarea (🧠). Borrar este bloque al crearla.
- Definición de Listo (para "Listas" como 🤖): esfuerzo S o M y diff < ~400 líneas / < ~8 archivos; sin decisiones
  de diseño abiertas; tests con un test de referencia del mismo tipo; "Leer antes" + "Archivos previstos" + AGENTS.md
  + STATUS.md + BACKLOG.md < ~40 % del contexto del 🤖 (~64K → `wc -c` total < ~90 KB); criterios verificables por
  comando o test; dependencias hechas.
- Va como 🧠: versión mayor, infra de tests/CI/contenedores, seguridad/autenticación, modelo de datos, refactors
  transversales, arquitectura, tareas que el 🤖 escaló. Ante la duda, 🧠.
- Definición de Hecho: tests en el PR; verify.sh y CI en verde; cobertura no baja; criterios marcados; Notas y
  Resultado; .example/README/SETUP al día; tablero y STATUS en el PR; revisión aprobada; squash merge.
-->
