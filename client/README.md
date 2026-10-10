# Lavadero · client

Front en React 18 + TypeScript + Vite, con Tailwind y NextUI. Visión general y paso a paso completo: [README raíz](../README.md).

## Correr solo el front

Todos los comandos desde `client/`, con Node 22 (`node -v` → `v22.x`; `nvm install` en la raíz lee `.nvmrc`).

**1. Dependencias:**

```bash
npm ci
```

Resultado esperado: `added 602 packages`.

**2. Servidor de desarrollo:**

```bash
npm run dev
```

Resultado esperado: `Local: http://localhost:5173/`. Al abrirlo se ve la landing.

> ⚠️ La URL de la API está hardcodeada a producción (`https://lavaderoweb.onrender.com/v1/api`) en 4 archivos. Para usar el back local hay que completar [T-003](../docs/tasks/T-003-configure-api-base-url.md).

**3. Lint y build:**

```bash
npm run lint
```

```bash
npm run build
```

Resultado esperado: el build termina con `✓ built in …`. ⚠️ Hoy el lint falla con 4 warnings: [T-001](../docs/tasks/T-001-fix-client-lint-warnings.md).

## Scripts

| Script | Para qué |
|---|---|
| `npm run dev` | Vite en :5173 con hot reload |
| `npm run build` | `tsc` + `vite build` → `dist/` |
| `npm run lint` | ESLint, falla con cualquier warning |
| `npm run preview` | Sirve `dist/` |
| `npm test` | (no existe todavía: [T-007](../docs/tasks/T-007-frontend-test-infrastructure.md)) |

## Estructura

```
src/
  main.tsx, App.tsx       Entrada y rutas (ProtectedRoute / UnauthenticatedRoute)
  services/Api.tsx        AuthProvider + useAuth (login, registro, token en localStorage)
  services/*API.ts        Contenido estático de la landing (FAQ, slides)
  pages/                  Una carpeta por ruta: index, login, register, dashboard
  views/                  Secciones de la landing y formularios de login/registro
  components/             NavBar, Footer, ClientVehicles (/clientes/:id), Turnos (/turnos/:id), …
  types/Types.ts          Tipos compartidos
```

Deploy: Vercel (`vercel.json` reescribe todo a `index.html`). Reglas para agentes: [AGENTS.md](AGENTS.md).
