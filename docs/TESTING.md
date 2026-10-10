# Estrategia de testing

El gate es `./scripts/verify.sh`. La infraestructura marcada _(T-NNN)_ todavía no existe: hasta que esa tarea se mergee, no la uses.

## 1. Pirámide y herramientas

**Back (`server/`)**
- **Unitarios** de servicios y dominio: JUnit 5, AssertJ, Mockito con mesura (mockear repos y colaboradores, no el objeto bajo prueba). Hoy: 16 tests en `service/impl/`.
- **Controllers**: `@WebMvcTest` + `spring-security-test` para validación, códigos HTTP, formato de error y acceso (401/403) _(T-013)_.
- **Repositorios**: `@DataJpaTest` solo para queries propias (`findByCorreoElectronico`, etc.).
- **Integración**: `@SpringBootTest` + **Testcontainers PostgreSQL** _(T-006)_. No H2: `Vehiculo.propiedadesAdicionales` es `jsonb` ([ADR 0001](adr/0001-testcontainers-postgres-over-h2.md)).
- **Cobertura**: JaCoCo con umbral en el `pom.xml` _(T-006)_.

**Front (`client/`)**
- **Vitest + React Testing Library** _(T-007)_: comportamiento visible, consultas por rol/label (`getByRole('button', { name: /agregar/i })`), nunca por clases CSS ni estado interno.
- **MSW** para simular la API _(T-007)_; los handlers se derivan del OpenAPI de springdoc (`/v3/api-docs`).
- **Cobertura**: proveedor v8 de Vitest _(T-007)_.

**E2E**
- **Playwright** _(T-012)_: pocos smoke tests de flujos críticos en viewport desktop (1280×800) y mobile (375×812), con screenshots y trazas en fallos. Sirven también como control de responsive.

## 2. Qué testear y qué no

- Sí: reglas de negocio (fecha futura del turno, cobro solo de turno FINALIZADO y único, email único), validaciones de DTO, permisos (público vs. autenticado), contratos de API (status + forma del JSON), estados de UI (loading / error / vacío / con datos).
- No: getters/setters, mappers MapStruct triviales por separado, código generado, el framework (que Spring inyecte un bean).

## 3. Convenciones

- Ubicación: el back espeja el paquete en `src/test/java` (`service/impl/XServiceImplTest.java`, `controller/XControllerTest.java`, `XIT.java` para integración). El front, junto al componente: `LoginForm.test.tsx`.
- Nombres: back `metodo_cuandoCondicion_resultado` (`createCobro_whenTurnoNotFinalizado_throws400`); front `it('muestra un error si el login falla')`.
- Arrange / Act / Assert separados por una línea en blanco. Un comportamiento por test.
- Datos: builders de Lombok (`Cliente.builder()…`) en `@BeforeEach`; si se repiten en 3+ clases, crear `src/test/java/…/support/TestData.java`.
- **Archivo de referencia por tipo** (copiá su estilo):
  - Unitario de servicio: `server/src/test/java/tech/munidigital/lavadero/service/impl/ClienteServiceImplTest.java`
  - Controller / integración / front / E2E: el primero lo crean T-013, T-006, T-007 y T-012; actualizá esta lista en ese PR.
- **Correr un solo test**:
  - Back: `cd server && ./mvnw -q test -Dtest='ClienteServiceImplTest#createCliente_whenEmailExists_throws400'`
  - Front _(T-007)_: `cd client && npx vitest run src/views/LoginView/LoginForm/LoginForm.test.tsx`
  - E2E _(T-012)_: `cd e2e && npx playwright test login.spec.ts`

## 4. Reglas para todas las tareas

- Todo cambio de comportamiento viene con sus tests en el mismo PR.
- **Bugfix = primero un test que reproduce el bug y falla**, después el fix (se ve en los commits).
- Antes de refactorizar o migrar algo sin tests: tests de caracterización que fijen el comportamiento **actual**, aunque sea raro (p. ej. hoy login con email inexistente → 404). Si el comportamiento es un bug, se documenta en el test y se corrige en otra tarea.
- **Prohibido** borrar, deshabilitar (`@Disabled`, `.skip`) o debilitar tests para que pase el build. Si un test está mal, se corrige y se justifica en el PR.
- Tests deterministas: sin dependencia de orden, de la hora (usar fechas relativas hasta que exista `Clock`), de la red ni de datos compartidos. Un test inestable es un bug con su tarea.

## 5. Cobertura como trinquete

- Base medida el 2026-10-10 (JaCoCo por CLI): **back 52,3 % líneas / 9,0 % ramas** (servicios 46,1 %). Front 0 %.
- T-006 fija el umbral de JaCoCo en la base redondeada hacia abajo (líneas 52 %, ramas 9 %); T-007 hace lo mismo con Vitest cuando exista el primer test.
- El umbral **nunca baja**. Cada auditoría lo sube si la cobertura real creció.
- Meta orientativa: ≥ 70–80 % en servicios/dominio y lógica de front; los flujos críticos (auditoría §4.7) siempre cubiertos de punta a punta. La cobertura es un piso, no el objetivo.

## 6. Opcional (auditorías)

Mutation testing puntual con PIT (back, `service/impl`) o Stryker (front, `services/`) en módulos críticos, para comprobar que los tests fallan cuando la lógica cambia.

## Roadmap de testing (antes de cualquier migración de versión)

1. 🧠 T-006 Infra back: JaCoCo + umbral, Testcontainers, perfil de test, primer test de integración.
2. 🧠 T-007 Infra front: Vitest + RTL + MSW, primer test.
3. 🧠 T-012 Infra E2E: Playwright + `scripts/e2e.sh` + job de CI, smoke del login.
4. 🤖 Caracterización: T-008 (login/registro), T-009 (turnos) y una tarea por controller/servicio sin tests.
