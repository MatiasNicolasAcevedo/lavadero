# Estado del proyecto
_Actualizado: 2026-10-10 · por: agente de frontera · sesión: auditoría (bootstrap T-000)_

## En una línea
Bootstrap en curso: PC2 alcanzado (auditoría completa; falta harness, docs y tablero).

## Salud
| Check | Estado | Nota |
|---|---|---|
| verify.sh | no existe todavía | se crea en este PR |
| CI en main | sin CI | |
| Levantar en local (README) | ❌ | README actual incompleto; build falla con JDK 21 |
| Cobertura back / front | 52,3 % / sin tests | medida con JaCoCo por CLI |
| E2E smoke | no existe | |
| Dependencias vulnerables | front: 6 críticas, 21 altas | `npm audit` |
| Última auditoría | 2026-10-10 (en curso) | [link](audits/2026-10-10-initial.md) |

## Fase actual
P0 Estabilizar · bootstrap en curso

## Foco
- PRs abiertos: T-000 bootstrap (draft)
- **Próximo paso exacto:** Fase 3 del bootstrap: harness (`scripts/`, toolchain, `.env`), README, docs → PC3.

## Decisiones pendientes del dueño
- Ver preguntas 1–5 en la [auditoría §5](audits/2026-10-10-initial.md#5-preguntas-abiertas-y-supuestos)

## Últimas sesiones
- 2026-10-10 · frontera · auditó (T-000) · PC2 · PR #33 en draft
