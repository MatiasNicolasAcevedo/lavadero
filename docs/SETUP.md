# Setup detallado

Complementa el [README](../README.md#levantar-el-proyecto-en-local) (no repite el paso a paso).

## Variables de entorno (back)

Se leen de `server/env.properties` (copiado de `server/env.properties.example`) o de variables de entorno del sistema (tienen el mismo nombre; en Render se cargan en el panel del servicio).

| Nombre | Para qué | Valor local | Obligatoria |
|---|---|---|---|
| `DB_URL` | JDBC de Postgres | `jdbc:postgresql://localhost:5432/lavadero` | sí |
| `DB_DRIVER_CLASS_NAME` | Driver JDBC | `org.postgresql.Driver` | sí |
| `DB_USER` / `DB_PASSWORD` | Credenciales | `lavadero_user` / `lavadero_pass` (las de `server/docker-compose.yml`) | sí |
| `DB_DIALECT` | Dialecto Hibernate | `org.hibernate.dialect.PostgreSQLDialect` | sí |
| `SECRET` | Clave HMAC del JWT, Base64 ≥ 32 bytes | la del ejemplo (solo local) · nueva: `openssl rand -base64 32` | sí |

El front no tiene variables todavía; T-003 agrega `VITE_API_URL` y `client/.env.example`.

## Sin Docker

Con un Postgres 16 instalado localmente (Linux):

```bash
sudo -u postgres psql -c "CREATE USER lavadero_user WITH PASSWORD 'lavadero_pass';" -c "CREATE DATABASE lavadero OWNER lavadero_user;"
```

Después seguí el README desde el paso 5. ⚠️ No verificado en esta sesión (se usó Docker).

## Puertos

| Puerto | Quién | Cómo cambiarlo |
|---|---|---|
| 5432 | Postgres | `ports: "5433:5432"` en `server/docker-compose.yml` (local, sin commitear) y `DB_URL=...:5433/...` en `server/env.properties` |
| 8080 | Back | `./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081` |
| 5173 | Front | `npm run dev -- --port 5174` |

## IDE

- **IntelliJ IDEA**: abrir `server/` como proyecto Maven; Project SDK = 17; activar *Annotation Processing* (Lombok y MapStruct). Run config de `LavaderoApplication` con *working directory* = `server/` para que lea `env.properties`.
- **VS Code**: extensiones *Extension Pack for Java*, *Spring Boot Extension Pack*, *ESLint*, *Tailwind CSS IntelliSense*, *EditorConfig*. `java.jdt.ls.java.home` apuntando a un JDK 17 si el default es otro.

## Debugging

- Back: `./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"` y adjuntar el IDE al puerto 5005.
- El log ya está en DEBUG para web/security/jackson (`application.properties:22-24`): buscá `Resolved [` para ver qué excepción terminó en 403.
- Front: React DevTools; los errores de API van a `console.error` con el nombre de la función (`fetchClients:`…).

## Resetear datos

El back recrea el esquema en cada arranque (`ddl-auto=create`) y el seed (`config/DataInitializer.java`) carga usuario demo, 5 clientes, 12 vehículos, turnos y cobros. Para borrar también el volumen: `docker compose -f server/docker-compose.yml down -v`.

## Agente ligero (Qwen Code u otro)

- **Contexto:** Qwen Code lee `QWEN.md` (puntero a `AGENTS.md`, sin duplicar). Para que lea `AGENTS.md` directo, en `~/.qwen/settings.json` configurá el nombre del archivo de contexto (`context.fileName: ["AGENTS.md"]`; en versiones viejas `contextFileName`) ⚠️ esquema no verificado en esta sesión. Cline/opencode/Aider: pasarle `AGENTS.md` como archivo de reglas o leído al inicio.
- **Modelo:** contexto efectivo ~64K: las tareas 🤖 están dimensionadas para usar < 40 % al empezar. Compactar con `/compress` (ver AGENTS.md § Gestión de contexto).
- **Arranque:** en una sesión nueva, escribir `Seguí docs/agents/implementer.md`.
- **GitHub:** `gh` CLI autenticado (`gh auth login`, permisos `repo`) para `gh pr create --draft`, `gh pr list`, `gh pr diff`, `gh pr comment`. Sin `gh`: `git push` + abrir el PR desde la web, y `git ls-remote --heads origin` para ver ramas tomadas.
- **Revisiones:** sin cuenta bot, la misma cuenta no puede aprobar su PR: se publica un comentario con veredicto (ver CONTRIBUTING § 4). Opcional: una cuenta bot o GitHub App con permiso de *pull requests* para revisar formalmente.
- **Permisos:** `.claude/settings.json` tiene la allowlist para Claude Code; en otras herramientas, aprobá solo `./scripts/verify.sh`, `./mvnw test`, `npm ci|run|test` y `git`/`gh` de lectura sin preguntar.
