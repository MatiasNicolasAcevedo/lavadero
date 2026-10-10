# T-013 · Respuestas de error y autenticación correctas (401/400 en vez de 403)
- Estado: Bloqueada (por T-006) · Prioridad: P0 · Esfuerzo: M · Agente: 🧠 frontera
- Fase: P0 Estabilizar
- Rama: `fix/T-013-fix-auth-error-responses` · Depende de: T-006 (perfil de test), T-008 (caracterización) · Spec/ADR: —
- Revisa: ligero (segunda mirada) + dueño

## Por qué
Probes de la auditoría (§3, §4.3, §4.6): sin token → 403; token malformado → 403 + stack trace; JSON inválido → 403 vacío (porque `/error` no es público, `SecurityConfig.java:81-88`); login con email inexistente → 404 `User does not exist` (`AppConfig.java:40-43`) y con password mala → 401 con el email en el mensaje (`LoginServiceImpl.java:57`): permite enumerar usuarios.

## Objetivo
| Caso | Hoy | Después |
|---|---|---|
| Sin token / token inválido o vencido | 403 | 401 |
| JSON inválido o tipo incorrecto | 403 vacío | 400 con cuerpo de error |
| Login con email inexistente o password mala | 404 / 401 con email | 401 con mensaje genérico idéntico |
| Errores de negocio (404/400 actuales) | iguales | iguales |

## Alcance
- Incluye: `AuthenticationEntryPoint` que devuelve 401 JSON; permitir `/error`; `JwtFilter` captura `JwtException` y sigue sin autenticar; `UserDetailsService` lanza `UsernameNotFoundException`; `LoginServiceImpl` con mensaje genérico; handler para `HttpMessageNotReadableException`; primer `@WebMvcTest` (referencia para controllers en `docs/TESTING.md`).
- **No incluye:** migrar a `ProblemDetail` (Backlog), roles, refresh tokens, CORS, cambios en el front (el manejo de 401 en el front es otra tarea del Backlog).

## Leer antes
- `server/src/main/java/tech/munidigital/lavadero/config/security/SecurityConfig.java` · `AppConfig.java` L38–44 · `config/jwt/JwtFilter.java` L51–92
- `service/impl/LoginServiceImpl.java` L43–78 · `exception/GlobalExceptionHandler.java`
- Tests de T-008 (se actualizan los que caracterizan el comportamiento viejo, justificándolo en el PR)

## Tests
- `@WebMvcTest(ClienteController.class)` + `@Import(SecurityConfig…)`: sin token → 401; token inválido → 401; JSON inválido → 400.
- Integración (`AuthFlowIT` de T-006): login email inexistente y password mala → mismo 401 y mismo cuerpo.
- Primero los tests en rojo (commit separado), después el fix.

## Criterios de aceptación
- [ ] Los 5 casos de la tabla cubiertos por tests y en verde
- [ ] `curl -s -o /dev/null -w '%{http_code}' http://localhost:8080/v1/api/clientes` → `401`
- [ ] Sin stack traces de `MalformedJwtException` en el log con un token inválido
- [ ] `./scripts/verify.sh` en verde; el front sigue funcionando (login y dashboard a mano)
- [ ] Gotcha del 403 eliminado de `AGENTS.md` y README

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: … · Pendientes → nuevas tareas: …
