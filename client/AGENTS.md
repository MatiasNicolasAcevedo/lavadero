# client/AGENTS.md — específico del front

Leé primero el [AGENTS.md raíz](../AGENTS.md). Esto agrega solo lo del front.

- Comandos desde `client/` con Node 22: `npm ci` · `npm run lint` (falla con cualquier warning) · `npm run build` (incluye `tsc`) · gate: `../scripts/verify.sh --client`.
- Rutas en `src/App.tsx`; las privadas van envueltas en `ProtectedRoute`. Sesión: `useAuth()` de `src/services/Api.tsx` (`token`, `user`, `login`, `logout`).
- Llamadas a la API: hoy `fetch` en cada componente con `API_URL` hardcodeada (hasta T-003 la base sale de `import.meta.env.VITE_API_URL`). No agregues `axios` (está sin uso y se elimina en T-004).
- Estilos: Tailwind en `className`; colores de marca `#007473` / `#005f60`. Componentes de NextUI solo donde ya se usan.
- Mobile first: probá cada pantalla a 375 px y 1280 px; sin scroll horizontal.
- Archivos caros: `src/components/Turnos.tsx` (412 líneas), `src/components/ClientVehicles.tsx` (323): leé rangos. Nunca abras `package-lock.json`.
- Tests: no hay runner hasta T-007. Después: Vitest + Testing Library + MSW, archivos `X.test.tsx` junto al componente (ver `docs/TESTING.md`).
