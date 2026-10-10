# T-007 · Infraestructura de tests del front: Vitest + Testing Library + MSW
- Estado: Lista · Prioridad: P0 · Esfuerzo: M · Agente: 🧠 frontera
- Fase: P0 Estabilizar
- Rama: `test/T-007-frontend-test-infrastructure` · Depende de: — (idealmente después de T-001 y T-003) · Spec/ADR: —
- Revisa: ligero (segunda mirada) + dueño

## Por qué
El front no tiene runner ni tests (auditoría §4.7): ninguna tarea 🤖 de front puede traer tests ni hay un ejemplo para copiar. El proyecto ya usa Vite, así que no hace falta ADR de migración desde CRA.

## Objetivo
`npm test` corre Vitest con jsdom, RTL y MSW, mide cobertura v8 con umbral, y hay un test de referencia de un formulario.

## Alcance
- Incluye: devDependencies (`vitest`, `@vitest/coverage-v8`, `jsdom`, `@testing-library/react`, `@testing-library/user-event`, `@testing-library/jest-dom`, `msw`) en versiones compatibles con Vite 5 / React 18; bloque `test` en `vite.config.ts`; `src/test/setup.ts` (jest-dom + server MSW); `src/test/handlers.ts` con `POST /auth/login` (éxito 200 y error 401); script `"test": "vitest run"` y `"test:watch"`; primer test `LoginForm.test.tsx`; umbral de cobertura = base medida con este test (redondeado hacia abajo).
- `scripts/verify.sh` ya ejecuta `npm test` si existe el script; actualizar `docs/TESTING.md` (referencia front, comando de un test) y `client/AGENTS.md`.
- **No incluye:** migrar a Vite 8 / React 19, refactorizar componentes para testearlos, tests de otras pantallas (tareas 🤖 posteriores).

## Leer antes
- `client/package.json`, `client/vite.config.ts`, `client/tsconfig.json`
- `client/src/views/LoginView/LoginForm/LoginForm.tsx` (90 líneas) · `client/src/services/Api.tsx` L50–85 (login)
- `docs/TESTING.md` § 1 y § 3

## Archivos previstos
- modificar: `client/package.json`, `client/package-lock.json`, `client/vite.config.ts`, `client/tsconfig.json` (types de vitest/jest-dom), `docs/TESTING.md`, `client/AGENTS.md`, `client/README.md`
- crear: `client/src/test/setup.ts`, `client/src/test/handlers.ts`, `client/src/test/renderWithProviders.tsx` (AuthProvider + MemoryRouter), `client/src/views/LoginView/LoginForm/LoginForm.test.tsx`

## Tests
- `LoginForm.test.tsx`: credenciales válidas → navega a `/dashboard` (o guarda el token); 401 → muestra el error; campos vacíos → mensajes de validación de react-hook-form.

## Pasos sugeridos
1. Instalar y configurar; `npx vitest run` con un test trivial.
2. MSW con la URL de `API_URL` (si T-003 está mergeada, la de `.env`; si no, la de prod).
3. Test real de `LoginForm`, cobertura, umbral.

## Criterios de aceptación
- [ ] `cd client && npm test` → 3 tests pasan, sin requests reales (MSW con `onUnhandledRequest: "error"`)
- [ ] `npm test -- --coverage` genera reporte y falla si se sube el umbral por encima de la cobertura real (probar y revertir)
- [ ] `./scripts/verify.sh --client` muestra `✅ tests`
- [ ] `docs/TESTING.md` lista `LoginForm.test.tsx` como referencia y el comando para un solo test

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: … · Pendientes → nuevas tareas: …
