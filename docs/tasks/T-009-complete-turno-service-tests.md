# T-009 · Completar los tests de TurnoServiceImpl
- Estado: Lista · Prioridad: P0 · Esfuerzo: S · Ejecuta: 🤖 agente gratuito
- Fase: P0 Estabilizar
- Rama: `test/T-009-complete-turno-service-tests` (crearla desde `main` actualizado antes del primer commit) · Depende de: — · Spec/ADR: —
- Revisa: 🤖 agente gratuito (chat nuevo)

## Por qué
`TurnoServiceImplTest` tiene 2 tests (alta OK y fecha pasada). No hay tests para vehículo inexistente, consultas ni cambio de estado (flujo crítico 7, auditoría §4.7).

## Objetivo
Todos los métodos públicos de `TurnoServiceImpl` con al menos un caso feliz y uno de error.

## Alcance
- Incluye: agregar tests a la clase existente.
- **No incluye:** validar transiciones de estado (hoy se permite FINALIZADO → EN_PROCESO; eso es Backlog con spec: acá se caracteriza tal cual), introducir `Clock`, tocar `src/main`.

## Leer antes
- `server/src/main/java/tech/munidigital/lavadero/service/impl/TurnoServiceImpl.java` L44–116
- `server/src/test/java/tech/munidigital/lavadero/service/impl/TurnoServiceImplTest.java` (completo, 100 líneas: es el archivo a extender)

## Archivos previstos
- modificar: `server/src/test/java/tech/munidigital/lavadero/service/impl/TurnoServiceImplTest.java`

## Tests
- `createTurno_whenVehiculoMissing_throws404` (y `turnoRepository.save` nunca se llama)
- `getTurnoById_whenExists_returnsResponse` · `getTurnoById_whenMissing_throws404`
- `getTurnosByVehiculoId_returnsMappedList` (2 turnos → 2 DTOs)
- `updateTurnoEstado_whenExists_setsNewStateAndSaves` (verificar con `ArgumentCaptor<Turno>` que se guarda con el estado nuevo)
- `updateTurnoEstado_whenMissing_throws404`
- `updateTurnoEstado_fromFinalizadoToEnProceso_isCurrentlyAllowed` (caracterización; comentario `// comportamiento actual, ver Backlog: validar transiciones`)

## Pasos sugeridos
1. Reusar el `@BeforeEach` existente (`requestDTO`, `vehiculo`, `turno`, `responseDTO`).
2. Correr solo esta clase: `cd server && ./mvnw -q test -Dtest=TurnoServiceImplTest`.

## Criterios de aceptación
- [ ] `./mvnw -q test -Dtest=TurnoServiceImplTest` → 9 tests, 0 fallos
- [ ] `./scripts/verify.sh --server` en verde (17 + 7 tests nuevos, más los de otras tareas ya mergeadas)
- [ ] `git diff --stat main -- server/src/main` vacío
- [ ] Si se comenta `turno.setEstado(nuevoEstado)` en `TurnoServiceImpl`, falla `updateTurnoEstado_whenExists_…` (probar localmente y revertir)

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: … · Pendientes → nuevas tareas: …
