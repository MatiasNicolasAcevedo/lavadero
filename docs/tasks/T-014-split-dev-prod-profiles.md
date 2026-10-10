# T-014 · Separar configuración de desarrollo y producción
- Estado: Bloqueada (decisión del dueño) · Prioridad: P0 · Esfuerzo: M · Ejecuta: 🧠 modelo de frontera
- Fase: P0 Estabilizar
- Rama: `refactor/T-014-split-dev-prod-profiles` (crearla desde `main` actualizado antes del primer commit) · Depende de: decisión del dueño, T-006 · Spec/ADR: crear ADR 0003 con la decisión
- Revisa: 🧠 modelo de frontera (sesión nueva, o sub-agente si el dueño lo pide) + 👤 dueño

## Por qué
Una sola configuración para todo (auditoría §4.3, §4.6): `ddl-auto=create` en prod borra la base en cada reinicio (`application.properties:11`); el seed (`config/DataInitializer.java`) corre siempre; logging DEBUG de security/web/jackson siempre (`:22-24`); `ServerWakeUpScheduler` pega a Render cada 14 min también desde local y tests (`util/ServerWakeUpScheduler.java:24,38`).

## Decisión pendiente del dueño
¿La demo pública debe **resetearse** en cada deploy (comportamiento actual) o **persistir** los datos que cargan los visitantes? Opciones: (a) persistir con `ddl-auto=validate` + Flyway y seed solo si la base está vacía; (b) resetear a propósito (perfil `demo` explícito). También: ¿se mantiene el keep-alive o se usa un monitor externo (UptimeRobot, cron de GitHub Actions)?

## Objetivo
Perfiles `dev` (default local) y `prod` (Render), comportamiento de esquema/seed según la decisión, logs INFO en prod, scheduler solo en prod y desactivable por propiedad.

## Alcance
- Incluye: `application-dev.properties`, `application-prod.properties`, `@Profile`/`@ConditionalOnProperty` en `DataInitializer` y `ServerWakeUpScheduler`, `spring.jpa.open-in-view=false`, variable `SPRING_PROFILES_ACTIVE=prod` documentada para Render, ADR 0003.
- **No incluye:** Flyway (si la decisión es (a), queda como tarea P2 aparte; mientras, `ddl-auto=update` en prod), seguridad (T-013), secreto (T-005).

## Leer antes
- `server/src/main/resources/application.properties` · `config/DataInitializer.java` L20–50 · `util/ServerWakeUpScheduler.java` · `server/Dockerfile` · `docs/SETUP.md`

## Criterios de aceptación
- [ ] Local (`./scripts/dev.sh`) igual que hoy: seed y usuario demo disponibles
- [ ] Con `SPRING_PROFILES_ACTIVE=prod` y la base con datos, reiniciar el back no borra datos (si la decisión es persistir) — probado con docker compose
- [ ] En tests y en dev no aparece `Health Check` en el log
- [ ] `./scripts/verify.sh` en verde; SETUP y ARCHITECTURE actualizados; ADR 0003 creado

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: … · Pendientes → nuevas tareas: …
