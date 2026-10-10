# T-010 · Proteger `main` y limpiar ramas (dueño)
- Estado: Lista · Prioridad: P0 · Esfuerzo: S · Agente: 👤 dueño
- Fase: P0 Estabilizar
- Rama: — (configuración en GitHub) · Depende de: merge de T-000 · Spec/ADR: —
- Revisa: —

## Por qué
`main` no tiene protección (auditoría §4.9) y hay 26 ramas remotas ya mergeadas que confunden a los agentes al buscar tareas tomadas (`git ls-remote --heads origin`).

## Pasos exactos (GitHub web, repo `MatiasNicolasAcevedo/lavadero`)
1. **Settings → General → Pull Requests**: desmarcar *Allow merge commits* y *Allow rebase merging*; dejar solo **Allow squash merging** con *Default commit message = Pull request title*; marcar **Always suggest updating pull request branches** y **Automatically delete head branches**.
2. **Settings → Rules → Rulesets → New ruleset → New branch ruleset**:
   - Name `main`, Enforcement **Active**, Target branches → *Add target* → **Include default branch**.
   - Marcar: **Restrict deletions**, **Require linear history**, **Require a pull request before merging** (Required approvals = **0**, porque la misma cuenta no puede aprobarse; la revisión de agentes queda como comentario), **Require status checks to pass** con **Require branches to be up to date** y el check `conventional-title` (aparece después de que corra una vez); **Block force pushes**.
   - Después de T-011, volver y agregar los checks `server` y `client` del workflow CI.
3. **Ramas viejas**: desde un clon actualizado, borrar las ramas ya mergeadas (todas salvo `main` y `feat/add-client-search-paged`, que tiene trabajo sin mergear y queda para P2):

```bash
git fetch --prune origin && git branch -r --merged origin/main | grep -v -E 'origin/(main|HEAD)' | sed 's#origin/##' | xargs -n1 git push origin --delete
```

## Criterios de aceptación
- [ ] Un `git push origin main` directo es rechazado
- [ ] Un PR de prueba solo ofrece "Squash and merge" y borra la rama al mergear
- [ ] `git ls-remote --heads origin` lista solo `main`, `feat/add-client-search-paged` y ramas de PRs abiertos
- [ ] `docs/STATUS.md` → Salud actualizado por el siguiente PR que toque el tablero

## Resultado
- Fecha: … · Notas: …
