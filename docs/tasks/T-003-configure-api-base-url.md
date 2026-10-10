# T-003 · Configurar la URL de la API del front por entorno
- Estado: Lista · Prioridad: P0 · Esfuerzo: S · Ejecuta: 🤖 agente gratuito
- Fase: P0 Estabilizar
- Rama: `feat/T-003-configure-api-base-url` (crearla desde `main` actualizado antes del primer commit) · Depende de: — (si T-001 ya se mergeó, rebasá) · Spec/ADR: —
- Revisa: 🧠 modelo de frontera (toca front, configuración y deploy)

## Por qué
`API_URL = "https://lavaderoweb.onrender.com/v1/api"` está hardcodeada en 4 archivos (`client/src/services/Api.tsx:27`, `client/src/pages/dashboard/Dashboard.tsx:12`, `client/src/components/ClientVehicles.tsx:26`, `client/src/components/Turnos.tsx:31`). En local el front nunca usa el back local (auditoría §4.4).

## Objetivo
En `npm run dev` y en `docker compose --profile app` el front llama al back local (`http://localhost:8080/v1/api`); en el build de Vercel sigue llamando a Render. La URL vive en configuración con su `client/.env.example` (regla de `AGENTS.md` § Configuración).

## Alcance
- Incluye: un único módulo de config, `client/.env.example`, tipos de `import.meta.env`, la variable en el servicio `client` de `docker-compose.yml`, docs.
- **No incluye:** crear una capa de API, cambiar `fetch` por otra cosa, manejo de 401 (Backlog), CORS del back, `vercel.json`.

## Leer antes
- Las 4 líneas citadas arriba (± 3 líneas)
- `client/src/vite-env.d.ts`
- `README.md` pasos 6–7 y "Problemas frecuentes" · `client/README.md`
- `scripts/dev.sh` (línea con `T-003`) · `docker-compose.yml` (servicio `client`)

## Archivos previstos
- crear: `client/src/services/config.ts`, `client/.env.example`
- modificar: los 4 archivos con `API_URL`, `client/src/vite-env.d.ts`, `.gitignore` (`client/.env`), `docker-compose.yml` (servicio `client`), `README.md` (§ Configurar variables de entorno), `client/README.md`, `docs/SETUP.md` (tabla de variables), `AGENTS.md` + `client/AGENTS.md` (gotcha y § Configuración), `scripts/dev.sh`

## Tests
- No hay runner en el front (T-007). Verificación: build + prueba manual + `grep`.

## Pasos sugeridos
1. `config.ts`: `export const API_URL = import.meta.env.VITE_API_URL;` y un `throw new Error("Falta VITE_API_URL")` si viene vacía.
2. `vite-env.d.ts`: `interface ImportMetaEnv { readonly VITE_API_URL: string }` + `interface ImportMeta { readonly env: ImportMetaEnv }`.
3. `client/.env.example`: `VITE_API_URL=http://localhost:8080/v1/api` con un comentario de para qué sirve y cómo copiarlo (`cp .env.example .env`, desde `client/`). El real `client/.env` va a `.gitignore`. `dev.sh` lo copia si falta. En `docker-compose.yml`, servicio `client`: `environment: VITE_API_URL: http://localhost:8080/v1/api` (el navegador llama al puerto publicado del back).
3b. Producción: **👤 dueño** define `VITE_API_URL=https://lavaderoweb.onrender.com/v1/api` en Vercel → Settings → Environment Variables (Production) **antes del merge**. El agente lo pide en el traspaso; no lo hace él.
4. Reemplazar las 4 constantes por `import { API_URL } from "<ruta>/services/config";`.
5. Docs: quitar los ⚠️ de T-003 (README paso a paso, Opción A, tabla de problemas, client/README, AGENTS gotcha, aviso en `dev.sh`); agregar la fila de `client/.env.example` al README § Configurar variables de entorno y `VITE_API_URL` a la tabla de SETUP (dónde se define en prod: Vercel).

## Criterios de aceptación
- [ ] `grep -rn "onrender.com" client/src` → sin resultados
- [ ] `client/.env.example` existe con `VITE_API_URL` comentada; `git check-ignore client/.env` → la ignora; copiarlo sin editar alcanza para local
- [ ] `cd client && VITE_API_URL=https://lavaderoweb.onrender.com/v1/api npm run build && grep -o "lavaderoweb.onrender.com/v1/api" dist/assets/*.js | head -1` → encuentra la URL de prod (lo que hará Vercel)
- [ ] Con `./scripts/dev.sh`: login con la cuenta demo en http://localhost:5173 → dashboard con 5 clientes; en Network las requests van a `localhost:8080`
- [ ] En `docker compose --profile app up -d --build`, el front usa el back del compose: login en http://localhost:5173 → requests a `localhost:8080` (no a Render)
- [ ] `./scripts/verify.sh --client` no empeora (lint puede seguir rojo si T-001 no está mergeada)
- [ ] README paso a paso sin ⚠️ de T-003, probado

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: — · Pendientes → nuevas tareas: …
