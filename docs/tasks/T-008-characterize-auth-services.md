# T-008 · Tests de caracterización de login y registro
- Estado: Lista · Prioridad: P0 · Esfuerzo: S · Agente: 🤖 ligero
- Fase: P0 Estabilizar
- Rama: `test/T-008-characterize-auth-services` · Depende de: — · Spec/ADR: —
- Revisa: ligero

## Por qué
Login y registro son los flujos críticos 1 y 2 (auditoría §4.7) y no tienen ningún test: `LoginServiceImpl` y `RegisterServiceImpl` están en 0 % de cobertura. Antes de corregir su comportamiento (T-013) hay que fijar el actual.

## Objetivo
Dos clases de test unitario que documentan el comportamiento **actual** de ambos servicios, incluidos los casos de error.

## Alcance
- Incluye: `LoginServiceImplTest` y `RegisterServiceImplTest` con Mockito, en el estilo de `ClienteServiceImplTest`.
- **No incluye:** cambiar código de producción (aunque un comportamiento parezca un bug: anotalo en "Notas" y en el PR), tests de controller o de `JwtService`, Testcontainers.

## Leer antes
- `server/src/main/java/tech/munidigital/lavadero/service/impl/LoginServiceImpl.java` L21–79
- `server/src/main/java/tech/munidigital/lavadero/service/impl/RegisterServiceImpl.java` L25–102
- `server/src/main/java/tech/munidigital/lavadero/dto/request/LoginRequestDTO.java` y `RegisterRequestDTO.java` (solo los campos)
- Ejemplo a copiar: `server/src/test/java/tech/munidigital/lavadero/service/impl/ClienteServiceImplTest.java`
- `docs/TESTING.md` § 3 y § 4

## Archivos previstos
- crear: `server/src/test/java/tech/munidigital/lavadero/service/impl/LoginServiceImplTest.java`, `.../RegisterServiceImplTest.java`

## Tests
`LoginServiceImplTest` (`@Mock UserRepository, JwtService, AuthenticationManager`):
- `login_whenCredentialsValid_returnsUserDataAndToken` → id, email, firstName, lastName y token = lo que devuelve `jwtService.generateToken(user)`.
- `login_whenBadCredentials_throws401` → `authenticationManager.authenticate` lanza `BadCredentialsException` → `ResponseStatusException` 401 (verificar también que el mensaje contiene el email: es el comportamiento actual).
- `login_whenUserMissingAfterAuth_throws404` → `findUserByEmail` vacío → 404.

`RegisterServiceImplTest` (`@Mock UserRepository, PasswordEncoder, JwtService`):
- `register_whenValid_savesEncodedPasswordAndReturnsToken` → con `ArgumentCaptor<User>`: la password guardada es la de `passwordEncoder.encode(...)`, nunca la original.
- `register_whenEmailExists_throws400` → `existsByEmail` true → 400 y `save` nunca se llama.
- `register_whenPhoneBlank_throws400` y `register_whenEmailBlank_throws400`.

## Pasos sugeridos
1. Copiar la estructura de `ClienteServiceImplTest` (anotaciones, `@BeforeEach` con builders, AssertJ).
2. Escribir un test, correrlo solo: `cd server && ./mvnw -q test -Dtest='LoginServiceImplTest'`; seguir con el resto.
3. Al final `./scripts/verify.sh --server`.

## Criterios de aceptación
- [ ] `cd server && ./mvnw -q test -Dtest='LoginServiceImplTest,RegisterServiceImplTest'` → 7 tests, 0 fallos
- [ ] `./scripts/verify.sh --server` → `tests (24)`
- [ ] Ningún archivo de `src/main` modificado (`git diff --stat main -- server/src/main` vacío)
- [ ] Si se rompe a propósito `passwordEncoder.encode(...)` (pasar la password cruda), `register_whenValid_…` falla (probalo localmente y revertí)

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: … · Pendientes → nuevas tareas: …
