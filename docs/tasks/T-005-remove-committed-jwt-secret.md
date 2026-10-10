# T-005 · Sacar el secreto JWT del repo y rotarlo
- Estado: Lista · Prioridad: P0 · Esfuerzo: S · Ejecuta: 🧠 modelo de frontera (+ acción del dueño)
- Fase: P0 Estabilizar
- Rama: `fix/T-005-remove-committed-jwt-secret` (crearla desde `main` actualizado antes del primer commit) · Depende de: — · Spec/ADR: —
- Revisa: 🧠 modelo de frontera (sesión nueva, o sub-agente si el dueño lo pide) + 👤 dueño

## Por qué
🔴 El valor de `SECRET` (clave HMAC de los JWT) está commiteado en `server/src/main/resources/application.properties:34` y `:48` (comentado) y estuvo en `README.md` (quitado en T-000). Sigue en el historial de git. Si Render usa ese valor, cualquiera puede firmar tokens válidos para cualquier usuario (auditoría §4.6).

## Objetivo
Ningún secreto en el árbol actual; producción con un secreto nuevo; el back no arranca si falta `SECRET` o si es demasiado corto.

## Alcance
- Incluye: borrar las líneas con el secreto (y los bloques comentados que dicen "descomentar líneas 27–34", ya obsoletos por `env.properties.example`); validación de la clave al arrancar; instrucciones de rotación.
- **No incluye:** reescribir el historial de git (no hace falta si se rota), cambiar el algoritmo, la expiración o el almacenamiento del token (Backlog), perfiles (T-014).

## Leer antes
- `server/src/main/resources/application.properties` (48 líneas)
- `server/src/main/java/tech/munidigital/lavadero/config/jwt/JwtService.java` L25–29 y L102–111
- `server/env.properties.example`, `docs/SETUP.md` § Variables

## Archivos previstos
- modificar: `application.properties`, `JwtService.java`, `docs/SETUP.md`
- crear: `server/src/test/java/tech/munidigital/lavadero/config/jwt/JwtServiceTest.java`

## Tests
- `JwtServiceTest` (unitario, sin Spring; setear `secretKey` con `ReflectionTestUtils`): token generado → `getUserName` devuelve el email; `validateToken` true para el mismo usuario y false para otro; clave < 32 bytes → excepción clara al validar la configuración.

## Pasos sugeridos
1. Borrar L26–34 y L36–48 de `application.properties` y dejar un comentario que apunte a `env.properties.example` y `docs/SETUP.md`.
2. En `JwtService`, `@PostConstruct` que decodifica la clave y falla con mensaje claro (`SECRET debe ser Base64 de al menos 32 bytes`).
3. `git grep -n "<prefijo del secreto viejo>"` en el árbol actual → vacío (no pegar el valor en el PR).
4. **Dueño** (en el PR, checklist): generar `openssl rand -base64 32`, reemplazar `SECRET` en Render → Environment, redeploy; los tokens viejos dejan de valer (re-login en la demo).

## Criterios de aceptación
- [ ] El valor viejo no aparece en ningún archivo del árbol (`git grep` por sus primeros 8 caracteres → vacío)
- [ ] Sin `SECRET`, `./mvnw spring-boot:run` falla al arrancar con un mensaje que nombra `SECRET`
- [ ] `JwtServiceTest` pasa; `./scripts/verify.sh --server` en verde
- [ ] El dueño confirma en el PR que rotó el secreto en Render y que la demo hace login

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: … · Pendientes → nuevas tareas: …
