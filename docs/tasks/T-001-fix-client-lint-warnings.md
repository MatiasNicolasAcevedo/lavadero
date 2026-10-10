# T-001 · Hacer que el lint del front pase sin warnings
- Estado: Lista · Prioridad: P0 · Esfuerzo: S · Ejecuta: 🤖 agente gratuito
- Fase: P0 Estabilizar
- Rama: `fix/T-001-fix-client-lint-warnings` (crearla desde `main` actualizado antes del primer commit) · Depende de: — · Spec/ADR: —
- Revisa: 🤖 agente gratuito (chat nuevo)

## Por qué
`npm run lint` falla (`--max-warnings 0`) con 4 warnings, así que `scripts/verify.sh` y la futura CI quedan en rojo (auditoría §4.10): 3× `react-hooks/exhaustive-deps` y 1× `react-refresh/only-export-components`.

## Objetivo
`cd client && npm run lint` termina sin errores ni warnings, sin cambiar el comportamiento de la app.

## Alcance
- Incluye: envolver los fetchers en `useCallback` y agregarlos a las dependencias del `useEffect`; mover el hook `useAuth` y el contexto fuera de `Api.tsx`.
- **No incluye:** cambiar la URL de la API (T-003), refactorizar a una capa de API, tocar `.eslintrc.cjs`, agregar `eslint-disable`, cambiar estilos o textos.

## Leer antes
- `client/src/pages/dashboard/Dashboard.tsx` L1–52 (fetchClients + useEffect)
- `client/src/components/ClientVehicles.tsx` L40–87 · `client/src/components/Turnos.tsx` L53–101
- `client/src/services/Api.tsx` L1–48 y L128–140 (contexto y hook)
- `client/AGENTS.md`

## Archivos previstos
- crear: `client/src/services/AuthContext.ts` (tipo `AuthContextType`, `AuthContext`), `client/src/services/useAuth.ts` (hook)
- modificar: `services/Api.tsx` (solo exporta `AuthProvider`), `Dashboard.tsx`, `ClientVehicles.tsx`, `Turnos.tsx`, y la línea de import de `useAuth` en `App.tsx`, `NavBar.tsx`, `LoginForm.tsx`, `RegisterForm.tsx` (cambios de 1 línea)

## Tests
- No hay runner de tests en el front (T-007). La verificación es lint + build + prueba manual (abajo).

## Pasos sugeridos
1. `AuthContext.ts`: mover `interface AuthContextType` y `const AuthContext = createContext<AuthContextType | undefined>(undefined)` (exportados). Sin JSX.
2. `useAuth.ts`: mover el hook tal cual (`useContext(AuthContext)` + error si falta provider), `export default useAuth`.
3. `Api.tsx`: importar `AuthContext` y dejar solo `export const AuthProvider`. Borrar el hook de ahí.
4. Actualizar imports: `import useAuth from "<ruta>/services/useAuth"`.
5. En cada componente: `const fetchX = useCallback(async () => { …igual… }, [token, <param de ruta>])` y `useEffect(…, [<deps actuales>, fetchX])`.
6. `npm run lint` y `npm run build` hasta que pasen.

## Criterios de aceptación
- [ ] `cd client && npm run lint` → sale con código 0 y sin warnings
- [ ] `cd client && npm run build` → `✓ built`
- [ ] `grep -rn "eslint-disable" client/src` → sin resultados
- [ ] `./scripts/verify.sh --client` → `client: ✅ lint ✅ build`
- [ ] Manual con `npm run dev`: login con la cuenta demo → dashboard lista clientes → "Ver Vehículos" → turnos; sin loops de requests en la pestaña Network

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: — · Pendientes → nuevas tareas: …
