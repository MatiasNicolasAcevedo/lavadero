# Estado del proyecto
_Actualizado: 2026-10-10 · por: agente de frontera (Claude) · sesión: auditoría (bootstrap T-000)_

## En una línea
Funciona de punta a punta en local con JDK 17; harness y tablero listos. Falta estabilizar: lint del front, JDK 21, front contra back local, secreto JWT, CI y red de tests.

## Salud
| Check | Estado | Nota |
|---|---|---|
| verify.sh | ❌ | server ✅ build ✅ tests (17) con JDK 17 · client ❌ lint (4) → T-001 · con JDK 21 ❌ build → T-002 |
| CI en main | sin CI | solo `pr-title.yml`; CI → T-011 |
| Levantar en local (README) | ✅ verificado 2026-10-10 (Linux) | con ⚠️: front usa la API de prod → T-003 |
| Cobertura back / front | 52,3 % líneas · 9,0 % ramas / sin tests | sin umbral todavía → T-006, T-007 |
| E2E smoke | no existe | → T-012 |
| Dependencias vulnerables | front: 6 críticas, 21 altas (npm audit) | → T-004 |
| Última auditoría | 2026-10-10 | [link](audits/2026-10-10-initial.md) |

## Fase actual
P0 Estabilizar · 0/15 tareas · ver [ROADMAP](ROADMAP.md)

## Foco
- PR abierto (máx. 1): #34 T-016 (draft, en curso: PC-A parcial)
- **Próximo paso:** T-016 · Alinear el harness con el prompt de bootstrap v3 · **ejecuta: 🧠 modelo de frontera**
- **Qué hacer:** abrí una sesión nueva de Claude Code y escribí: «Seguí con T-016 desde sus Notas de implementación (rama `chore/T-016-align-harness-v3`, PR #34)»
- **Después:** T-005 (🧠) → T-001 (🤖) → T-003 (🤖)

## Decisiones pendientes del dueño
- ¿El `SECRET` de Render es el valor que estaba commiteado? Si sí, rotarlo ya (T-005).
- ¿La demo se resetea en cada deploy o persiste datos? (bloquea T-014)
- ¿Se mantiene el branding MuniDigital (logo, redes, textos) en el portfolio?
- ¿Keep-alive contra Render o monitor externo? (T-014)
- Licencia del repo (MIT sugerida) · ¿cuenta bot / GitHub App para revisiones formales?

## Últimas sesiones (máx. 5; las anteriores → archive/log/AAAA-MM.md)
- 2026-10-10 · Claude (frontera) · auditó T-000 · auditoría + harness + tablero P0 (15 tareas) · PR #33 (revisado en la misma sesión y mergeado a pedido del dueño)
