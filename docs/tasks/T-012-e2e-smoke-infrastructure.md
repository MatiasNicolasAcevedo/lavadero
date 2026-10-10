# T-012 · Infraestructura E2E con Playwright y smoke del login
- Estado: Bloqueada (por T-003) · Prioridad: P0 · Esfuerzo: M · Agente: 🧠 frontera
- Fase: P0 Estabilizar
- Rama: `test/T-012-e2e-smoke-infrastructure` · Depende de: T-003, T-011 · Spec/ADR: —
- Revisa: ligero (segunda mirada) + dueño

## Por qué
El flujo 9 de la auditoría (login → dashboard → vehículos → turnos) no tiene test; además el E2E en 375 px es el control de responsive del proyecto. En T-000 se verificó a mano con Playwright redirigiendo la URL de producción, lo que deja de hacer falta con T-003.

## Objetivo
`./scripts/e2e.sh` levanta DB + back + front, corre los smoke tests en desktop (1280×800) y mobile (375×812) y sale ≠ 0 si fallan; job `e2e` en CI con el reporte de Playwright como artefacto si falla.

## Alcance
- Incluye: carpeta `e2e/` con su `package.json` (`@playwright/test`), `playwright.config.ts` (2 proyectos de viewport, `trace: 'retain-on-failure'`, `screenshot: 'only-on-failure'`, `webServer` o reutilizar `scripts/dev.sh`), `login.spec.ts` (login demo → ve "Juan Pérez" → "Ver Vehículos" → "Toyota Corolla" → turnos), `scripts/e2e.sh`, job en `ci.yml`, docs (`docs/TESTING.md` referencia E2E, README "Tests y calidad", `AGENTS.md`).
- **No incluye:** más specs (tareas 🤖 posteriores), visual regression, cambios de UI.

## Leer antes
- `scripts/dev.sh` · `.github/workflows/ci.yml` · `docs/TESTING.md` § 1 · `client/src/App.tsx` (rutas)

## Criterios de aceptación
- [ ] `./scripts/e2e.sh` desde cero → 2 tests (desktop + mobile) en verde, y apaga back/front al terminar
- [ ] Romper a propósito el texto del botón "Ver Vehículos" hace fallar el smoke (probar y revertir)
- [ ] Job `e2e` en verde en CI y sube `playwright-report/` si falla
- [ ] `docs/TESTING.md` actualizado con el archivo de referencia y cómo correr un spec

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: … · Pendientes → nuevas tareas: …
