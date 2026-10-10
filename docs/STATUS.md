# Estado del proyecto
_Actualizado: 2026-10-10 · por: agente de frontera · sesión: auditoría (bootstrap T-000)_

## En una línea
Bootstrap en curso: PC1 alcanzado (inventario y verificación hechos; falta relevamiento por dimensiones y harness).

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
- **Próximo paso exacto:** completar la Fase 2 de la auditoría (relevamiento por dimensiones) → PC2.

## Decisiones pendientes del dueño
- (se completan en el PC2)

## Últimas sesiones
- 2026-10-10 · frontera · auditó (T-000) · PC1 · PR en draft
