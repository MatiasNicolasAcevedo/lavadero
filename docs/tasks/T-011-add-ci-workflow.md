# T-011 · Agregar CI que corra el gate en cada push y PR
- Estado: Bloqueada (por T-001) · Prioridad: P0 · Esfuerzo: S · Agente: 🧠 frontera
- Fase: P0 Estabilizar
- Rama: `ci/T-011-add-ci-workflow` · Depende de: T-001 (lint) · Spec/ADR: —
- Revisa: ligero (segunda mirada) + dueño

## Por qué
No hay CI (auditoría §4.8). No se agregó en T-000 porque `scripts/verify.sh` hoy falla por el lint del front; un workflow rojo desde el día uno no sirve como gate.

## Objetivo
`.github/workflows/ci.yml` en verde en el PR y en `main`, con jobs `server` y `client`, y reportes como artefactos.

## Alcance
- Incluye: el workflow propuesto abajo, badge de CI en el README, actualizar T-010 (agregar checks requeridos) y `docs/STATUS.md`.
- **No incluye:** E2E (lo agrega T-012), deploy, Dependabot (T-015).

## Leer antes
- `scripts/verify.sh` · `.github/workflows/pr-title.yml` · `docs/tasks/T-010-protect-main-branch.md` paso 2

## Archivos previstos
- crear: `.github/workflows/ci.yml` · modificar: `README.md` (badge), `docs/STATUS.md`

## YAML propuesto (validar versiones de las actions al implementar)

```yaml
name: CI
on:
  push:
    branches: [main]
  pull_request:
concurrency:
  group: ci-${{ github.ref }}
  cancel-in-progress: true
permissions:
  contents: read
jobs:
  server:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with: { distribution: temurin, java-version: '17', cache: maven }
      - run: ./scripts/verify.sh --server   # Docker viene en ubuntu-latest (compose o Testcontainers)
      - if: always()
        uses: actions/upload-artifact@v4
        with:
          name: server-reports
          path: |
            server/target/site/jacoco/
            server/target/surefire-reports/
            .verify-logs/
          if-no-files-found: ignore
  client:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with: { node-version-file: .nvmrc, cache: npm, cache-dependency-path: client/package-lock.json }
      - run: ./scripts/verify.sh --client
      - if: failure()
        uses: actions/upload-artifact@v4
        with: { name: client-logs, path: .verify-logs/, if-no-files-found: ignore }
```

## Criterios de aceptación
- [ ] Los jobs `server` y `client` pasan en el PR (link al run en la descripción)
- [ ] Un commit de prueba con un test roto hace fallar `server` (probar en el PR y revertir)
- [ ] Badge `![CI](https://github.com/MatiasNicolasAcevedo/lavadero/actions/workflows/ci.yml/badge.svg)` en el README
- [ ] T-010 actualizada para requerir `server` y `client`

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: — · Pendientes → nuevas tareas: …
