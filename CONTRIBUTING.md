# Cómo contribuir

Vale igual para personas y agentes. Los agentes siguen además los protocolos de [docs/agents/](docs/agents/).

## 1. Ramas — GitHub Flow

- `main` siempre en verde y deployable. **Nadie commitea directo a `main`.**
- Una rama corta por tarea, desde `main` actualizado:

  ```bash
  git switch main && git pull && git switch -c feat/T-012-e2e-login-smoke
  ```

- Formato: `<tipo>/T-NNN-slug-corto` con `tipo` ∈ `feat`, `fix`, `test`, `refactor`, `chore`, `docs`, `ci`, `build`, `perf`. El nombre exacto está en la tarea.
- Vida corta (< 2 días). Si `main` avanzó: `git fetch origin && git rebase origin/main` antes de pedir revisión (en tu propia rama; `git push --force-with-lease`).

## 2. Commits — Conventional Commits (en inglés)

```
tipo(alcance): descripción en imperativo, minúscula, sin punto

Por qué (opcional, 1–3 líneas).

Refs: T-NNN
```

- Alcance: `server`, `client`, `ci`, `docs`, `board`, `deps` o el módulo.
- Cambios incompatibles: `feat(server)!: …` y footer `BREAKING CHANGE: …`.
- Atómicos: cada commit compila y tiene sentido solo. Prohibido `wip`, `fix`, `cambios`, `update`.

Ejemplos: `fix(client): add useCallback to fetchers in dashboard` · `test(server): characterize login service` · `docs(board): start T-008`.

## 3. Pull requests

- **Un PR por tarea**, chico (orientativo < 400 líneas sin lockfiles ni generados). Si crece, se parte la tarea.
- **Título = Conventional Commit** (lo valida `.github/workflows/pr-title.yml`), porque se mergea con squash y queda como commit en `main`.
- Se abre en **draft apenas se crea la rama** (con el commit `docs(board): start T-NNN`): así la tarea queda tomada y visible.
- Pasa a **ready for review** con `./scripts/verify.sh` en verde y la descripción completa según `.github/pull_request_template.md`: tarea, qué y por qué, cómo se verificó, capturas desktop (1280 px) + mobile (375 px) si hay UI, riesgos.
- En el mismo PR: archivo de tarea ("Notas" y "Resultado"), `docs/BACKLOG.md` y `docs/STATUS.md` actualizados.

## 4. Revisión

Todo PR lo revisa un agente **en una sesión nueva** con [docs/agents/reviewer.md](docs/agents/reviewer.md):

| PR | Implementa | Revisa |
|---|---|---|
| Tarea 🤖 de esfuerzo S, sin riesgo | ligero | ligero en sesión nueva |
| Tarea 🤖 M, o que toca back y front, seguridad, datos o CI | ligero | frontera |
| Tarea 🧠 | frontera | ligero en sesión nueva (segunda mirada) + dueño |
| Dependabot | bot | ligero; si es versión mayor → tarea 🧠 |

- Máximo **2 rondas** de cambios; después se escala a frontera o al dueño.
- Veredicto explícito: `APROBADO` / `CAMBIOS REQUERIDOS` / `ESCALAR A 🧠/DUEÑO`.
- Limitación de GitHub: con la misma cuenta no se puede aprobar formalmente el propio PR. Sin cuenta bot, la revisión se publica como **comentario** con el veredicto en la primera línea.

## 5. Merge

- **Squash merge**, solo con CI en verde y revisión `APROBADO`. Lo hace el dueño.
- Borrar la rama después del merge.
- Si el PR se cierra sin mergear, el siguiente PR que toque el tablero devuelve la tarea a "Listas" o "Bloqueadas" con el motivo.

## 6. Protección de `main` (la configura el dueño)

Pasos exactos en [T-010](docs/tasks/T-010-protect-main-branch.md): requerir PR, requerir checks de CI, rama actualizada, historia lineal, sin force push, solo squash merge y borrar ramas al mergear.

## 7. Releases

Al cerrar cada fase del [ROADMAP](docs/ROADMAP.md): tag semántico `v0.x.0` y release en GitHub con notas generadas de los títulos de los PR:

```bash
gh release create v0.1.0 --generate-notes --title "v0.1.0 · P0 Estabilizar"
```

Las auditorías se marcan con `audit/AAAA-MM-DD` después de mergear su PR.

## 8. Dependencias

- Dependabot (`.github/dependabot.yml`, se activa en T-015 cuando CI esté en verde): Maven, npm y GitHub Actions, semanal, minor/patch agrupados.
- Los PR de Dependabot se revisan como cualquier otro; una versión mayor se convierte en tarea 🧠 con su ADR si cambia la arquitectura.
- Nunca subir versiones mayores "de paso" en otra tarea.
