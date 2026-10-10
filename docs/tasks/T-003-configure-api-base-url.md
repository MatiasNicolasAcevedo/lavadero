# T-003 · Configurar la URL de la API del front por entorno
- Estado: Lista · Prioridad: P0 · Esfuerzo: S · Agente: 🤖 ligero
- Fase: P0 Estabilizar
- Rama: `feat/T-003-configure-api-base-url` · Depende de: — (si T-001 ya se mergeó, rebasá) · Spec/ADR: —
- Revisa: frontera (toca front + deploy)

## Por qué
`API_URL = "https://lavaderoweb.onrender.com/v1/api"` está hardcodeada en 4 archivos (`client/src/services/Api.tsx:27`, `client/src/pages/dashboard/Dashboard.tsx:12`, `client/src/components/ClientVehicles.tsx:26`, `client/src/components/Turnos.tsx:31`). En local el front nunca usa el back local (auditoría §4.4).

## Objetivo
En `npm run dev` el front llama a `http://localhost:8080/v1/api`; en `npm run build` (Vercel) sigue llamando a Render. Sin pasos manuales nuevos.

## Alcance
- Incluye: un único módulo de config, archivos `.env.*` de Vite, tipos de `import.meta.env`, docs.
- **No incluye:** crear una capa de API, cambiar `fetch` por otra cosa, manejo de 401 (Backlog), CORS del back, `vercel.json`.

## Leer antes
- Las 4 líneas citadas arriba (± 3 líneas)
- `client/src/vite-env.d.ts`
- `README.md` pasos 6–7 y "Problemas frecuentes" · `client/README.md`
- `scripts/dev.sh` (línea con `T-003`)

## Archivos previstos
- crear: `client/src/services/config.ts`, `client/.env.development`, `client/.env.production`, `client/.env.example`
- modificar: los 4 archivos con `API_URL`, `client/src/vite-env.d.ts`, `README.md`, `client/README.md`, `docs/SETUP.md` (tabla de variables), `AGENTS.md` + `client/AGENTS.md` (gotcha), `scripts/dev.sh`

## Tests
- No hay runner en el front (T-007). Verificación: build + prueba manual + `grep`.

## Pasos sugeridos
1. `config.ts`: `export const API_URL = import.meta.env.VITE_API_URL;` y un `throw new Error("Falta VITE_API_URL")` si viene vacía.
2. `vite-env.d.ts`: `interface ImportMetaEnv { readonly VITE_API_URL: string }` + `interface ImportMeta { readonly env: ImportMetaEnv }`.
3. `.env.development`: `VITE_API_URL=http://localhost:8080/v1/api` · `.env.production`: `VITE_API_URL=https://lavaderoweb.onrender.com/v1/api` · `.env.example`: ambas comentadas, explicando que `.env.development.local` (ignorado por `*.local`) permite pisarla.
4. Reemplazar las 4 constantes por `import { API_URL } from "<ruta>/services/config";`.
5. Docs: quitar los ⚠️ de T-003 (README pasos 6–7, tabla de problemas, client/README, AGENTS gotcha, aviso en `dev.sh`); agregar `VITE_API_URL` a la tabla de SETUP.

## Criterios de aceptación
- [ ] `grep -rn "onrender.com" client/src` → sin resultados
- [ ] `cd client && npm run build && grep -o "lavaderoweb.onrender.com/v1/api" dist/assets/*.js | head -1` → encuentra la URL de prod
- [ ] Con `./scripts/dev.sh`: login con la cuenta demo en http://localhost:5173 → dashboard con 5 clientes; en Network las requests van a `localhost:8080`
- [ ] `./scripts/verify.sh --client` no empeora (lint puede seguir rojo si T-001 no está mergeada)
- [ ] README paso a paso sin ⚠️ de T-003, probado

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: — · Pendientes → nuevas tareas: …
