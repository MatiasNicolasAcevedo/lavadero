# T-004 · Quitar dependencias sin uso del front
- Estado: Lista · Prioridad: P0 · Esfuerzo: S · Agente: 🤖 ligero
- Fase: P0 Estabilizar
- Rama: `chore/T-004-remove-unused-client-deps` · Depende de: — · Spec/ADR: —
- Revisa: ligero

## Por qué
`npm audit`: 40 vulnerabilidades (6 críticas). Las críticas vienen del paquete espurio `package.json@^2.0.1` (`client/package.json:17`, deprecado, trae `git-url-parse`/`parse-url`) y de `form-data` vía `axios`, que no se usa. Tampoco se importan `slick-carousel`, `date-fns`, `react-datepicker` ni `@types/react-datepicker` (`grep` en `client/src` = 0) (auditoría §4.2).

## Objetivo
Esas 6 dependencias fuera de `package.json` y del lockfile; `npm audit` sin vulnerabilidades críticas.

## Alcance
- Incluye: `npm uninstall` de las 6 y regenerar `package-lock.json` con npm.
- **No incluye:** actualizar otras dependencias ni `npm audit fix` (Backlog P1), renombrar el paquete (`coworkproject`), tocar código.

## Leer antes
- `client/package.json` (completo, 44 líneas). **No abrir** `package-lock.json`.

## Archivos previstos
- modificar: `client/package.json`, `client/package-lock.json` (generado), `docs/STATUS.md` (fila "Dependencias vulnerables")

## Tests
- Sin tests nuevos. Comprobar que nada importaba esos paquetes (ver criterios).

## Pasos sugeridos
1. Confirmar 0 usos: `cd client && grep -rnE "from ['\"](axios|slick-carousel|date-fns|react-datepicker)" src` → vacío.
2. `npm uninstall package.json axios slick-carousel date-fns react-datepicker @types/react-datepicker`
3. `npm run build` y `npm audit --omit=dev` / `npm audit`; anotar los números nuevos en la descripción del PR y en STATUS.

## Criterios de aceptación
- [ ] `npm ls axios package.json slick-carousel date-fns react-datepicker` → `(empty)`
- [ ] `npm audit` → 0 críticas (anotar totales antes/después en el PR)
- [ ] `npm run build` → `✓ built`
- [ ] `./scripts/verify.sh --client` no empeora respecto de `main`
- [ ] El diff de `package.json` solo quita esas 6 líneas

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: — · Pendientes → nuevas tareas: …
