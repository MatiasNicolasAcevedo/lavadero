# Lavadero · Sistema de gestión para un lavadero de autos 🚗✨

Aplicación full-stack para gestionar clientes, vehículos, turnos y cobros de un lavadero: API REST en **Java + Spring Boot + PostgreSQL** y front en **React + TypeScript + Vite**.

![Licencia](https://img.shields.io/badge/licencia-pendiente-lightgrey) <!-- badges de CI y cobertura: T-011 -->

[![Landing Page](https://github.com/user-attachments/assets/ef782d5a-d3a4-4d54-ad76-e61f5cc63329)](https://lavadero-munidigital.vercel.app)

_Click en la imagen para abrir la demo online. Capturas del dashboard en desktop y mobile: `docs/images/` (pendiente, P3)._

> [!IMPORTANT]
> Cuenta demo (también se crea en local con el seed):<br>
> Email: `lavadero@munidigital.com` · Contraseña: `12345Aa*`

- **Demo online:** [lavadero-munidigital.vercel.app](https://lavadero-munidigital.vercel.app) (el back en Render puede tardar ~1 min en despertar)
- **API (Swagger):** [lavaderoweb.onrender.com/swagger-ui/index.html](https://lavaderoweb.onrender.com/swagger-ui/index.html#/)
- **Playlist del proyecto:** [![YouTube](https://img.shields.io/badge/YouTube-Playlist-red?logo=youtube)](https://youtube.com/playlist?list=PLkNS1qPGkDME9XgneSvzi2yYZ_AQmUOdF&si=nE-MgutukRJn5So_)

## Qué resuelve

Un lavadero necesita registrar a sus clientes y vehículos, agendar turnos, seguir su estado y cobrarlos. Features:

- Registro e inicio de sesión de usuarios con JWT.
- Clientes: alta, listado y búsqueda por nombre.
- Vehículos por cliente, con propiedades dinámicas (color, año, etc.).
- Turnos por vehículo con estados `PENDIENTE → EN_PROCESO → FINALIZADO`.
- Cobro de turnos finalizados (uno por turno).
- 🚧 En progreso: búsqueda paginada, edición/baja, listado de cobros ([BACKLOG](docs/BACKLOG.md)).

## Stack

| Parte | Tecnologías |
|---|---|
| Back (`server/`) | Java 17 · Spring Boot 3.3.1 (Web, Data JPA, Security, Validation) · JWT (jjwt 0.12) · MapStruct · Lombok · springdoc-openapi · Maven |
| Base de datos | PostgreSQL 16 (Docker Compose en local) |
| Front (`client/`) | React 18 · TypeScript 5 · Vite 5 · React Router 6 · Tailwind CSS 3 · NextUI · react-hook-form |
| Deploy | Back en Render (Docker) · Front en Vercel |

Arquitectura, modelo de datos y endpoints: [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md).

## Levantar el proyecto en local

### Prerrequisitos

| Herramienta | Versión | Cómo comprobarla | Cómo instalarla |
|---|---|---|---|
| Git | cualquiera reciente | `git --version` | [git-scm.com](https://git-scm.com/downloads) |
| JDK | **17** (con 21 el build falla hasta [T-002](docs/tasks/T-002-upgrade-lombok-for-jdk21.md)) | `java -version` → `17.x` | [SDKMAN](https://sdkman.io): `sdk env install` en la raíz del repo (lee `.sdkmanrc`) · o [Temurin 17](https://adoptium.net/temurin/releases/?version=17) |
| Node.js | 22 (npm 10) | `node -v` → `v22.x` | [nvm](https://github.com/nvm-sh/nvm): `nvm install` en la raíz (lee `.nvmrc`) · Windows: [nvm-windows](https://github.com/coreybutler/nvm-windows) |
| Docker + Compose v2 | Docker 24+ | `docker compose version` | [Docker Desktop](https://www.docker.com/products/docker-desktop/) (abierto antes de empezar) |

Maven no hace falta: se usa el wrapper `./mvnw`. Puertos que se usan: **5432** (Postgres), **8080** (back), **5173** (front).

### Inicio rápido

Desde la raíz del repo, con los prerrequisitos instalados:

```bash
./scripts/dev.sh
```

Resultado esperado: termina mostrando `✅ Entorno listo` con las URLs. Abrí http://localhost:5173. `Ctrl+C` corta back y front; `./scripts/dev.sh --down` apaga la base.

### Paso a paso

Es exactamente lo que hace `./scripts/dev.sh`. Cada comando indica desde qué carpeta se corre.

**1. Clonar el repositorio** — desde la carpeta donde guardás proyectos.

```bash
git clone https://github.com/MatiasNicolasAcevedo/lavadero.git
```

```bash
cd lavadero
```

Resultado esperado: `ls` muestra `client  docs  scripts  server  README.md …`.

**2. Fijar las versiones de Java y Node** — desde `lavadero/`. Con SDKMAN y nvm instalados:

```bash
sdk env install
```

```bash
nvm install
```

Resultado esperado: `java -version` muestra `17.x` y `node -v` muestra `v22.x`. Sin SDKMAN/nvm, instalá esas versiones a mano (tabla de prerrequisitos).

**3. Crear la configuración local del back** — desde `lavadero/`. Se copia tal cual, sin editar:

```bash
cp server/env.properties.example server/env.properties
```

Resultado esperado: existe `server/env.properties` (está en `.gitignore`; Spring lo lee al arrancar).

**4. Levantar la base de datos** — desde `lavadero/`:

```bash
docker compose -f server/docker-compose.yml up -d --wait
```

Resultado esperado: `Container lavadero-postgres Healthy`.

**5. Levantar el back** — en una terminal nueva, desde `lavadero/server/`:

```bash
./mvnw spring-boot:run
```

Resultado esperado: la primera vez descarga Maven y dependencias (2–5 min); termina con `Started LavaderoApplication` y `Seed dev completado con éxito.` Para comprobarlo desde otra terminal:

```bash
curl http://localhost:8080/v1/api/render/health
```

Debe responder `Server is awake and running!`. Swagger: http://localhost:8080/swagger-ui/index.html

**6. Levantar el front** — en otra terminal, desde `lavadero/client/`:

```bash
npm ci
```

```bash
npm run dev
```

Resultado esperado: `VITE ... ready` y `Local: http://localhost:5173/`. Abrí esa URL y deberías ver la landing.

> ⚠️ **Hoy el front local usa la API de producción** (URL hardcodeada), no el back del paso 5: ver [T-003](docs/tasks/T-003-configure-api-base-url.md). Hasta entonces, el login desde el navegador depende de que Render esté arriba.

**7. Iniciar sesión con el usuario de prueba contra el back local** — desde cualquier carpeta:

```bash
curl -X POST http://localhost:8080/v1/api/auth/login -H "Content-Type: application/json" -d "{\"email\":\"lavadero@munidigital.com\",\"password\":\"12345Aa*\"}"
```

Resultado esperado: un JSON con `"token":"eyJ..."` y `"firstName":"Muni"`. En el navegador: http://localhost:5173/login con la cuenta demo → `/dashboard` con 5 clientes (ver nota del paso 6).

**8. Correr las verificaciones** — desde `lavadero/` (con la base del paso 4 arriba):

```bash
./scripts/verify.sh
```

Resultado esperado: una línea de resumen y `VERIFY OK`. ⚠️ Hoy falla el lint del front (`client: ❌ lint (4 problems)`): ver [T-001](docs/tasks/T-001-fix-client-lint-warnings.md). El back debe dar `server: ✅ build ✅ tests (17)`.

**9. Apagar todo y limpiar** — `Ctrl+C` en las terminales del back y del front; después, desde `lavadero/`:

```bash
docker compose -f server/docker-compose.yml down -v
```

Resultado esperado: `Container lavadero-postgres Removed` y `Volume server_lavadero_pg_data Removed` (se borran los datos; el seed los recrea al volver a levantar el back).

### Windows

- Usá **Git Bash** o **WSL** para `./scripts/*.sh` y los comandos `cp`/`curl` de arriba.
- En PowerShell/CMD el back se levanta con `mvnw.cmd spring-boot:run` (desde `server\`) y el paso 3 es `copy server\env.properties.example server\env.properties`.
- Si `./mvnw` da `bad interpreter` o `\r`, configurá `git config --global core.autocrlf input` y volvé a clonar.
- ⚠️ No verificado en Windows todavía.

### Problemas frecuentes

| Síntoma | Causa | Solución |
|---|---|---|
| `Fatal error compiling: java.lang.NoSuchFieldError: ... JCTree` | Compilando con JDK 21+ (Lombok 1.18.26) | Usar JDK 17 (`sdk env install`) hasta T-002 |
| `./mvnw: Permission denied` | Clon viejo sin bit de ejecución | `chmod +x server/mvnw` (ya corregido en `main`) |
| `Cannot load driver class: ${DB_DRIVER_CLASS_NAME}` | Falta `server/env.properties` | Paso 3 |
| `Connection to localhost:5432 refused` | La base no está levantada o Docker Desktop cerrado | Paso 4; `docker compose -f server/docker-compose.yml ps` |
| `port is already allocated` (5432) | Otro Postgres local usando el puerto | Detenerlo, o ver `docs/SETUP.md` § Puertos |
| `Port 8080 was already in use` | Otra app (o un back anterior) en 8080 | Cerrarla; `lsof -i :8080` para encontrarla |
| Descarga de Maven falla (`429`, timeout) | Red/proxy | Reintentar `./mvnw -v` |
| El front muestra listas vacías o el login falla en local | El front llama a producción | T-003; probar el back local con el paso 7 |
| Cualquier error del back devuelve `403` vacío | `/error` protegido por Spring Security | Mirar el log del back; se corrige en T-013 |

Verificado el 2026-10-10 en Linux (Ubuntu 24.04) con Java 17.0.20, Node 22.22, Docker 29.8.

## Tests y calidad

`./scripts/verify.sh` es el único comando que decide si un cambio pasa (`--server` o `--client` para correr una parte). Estrategia, convenciones y cómo correr un solo test: [docs/TESTING.md](docs/TESTING.md).

## Cómo se trabaja en el proyecto

Ramas cortas por tarea, Conventional Commits y PR con revisión: [CONTRIBUTING.md](CONTRIBUTING.md). El repo está preparado para desarrollo asistido por agentes de IA: instrucciones en [AGENTS.md](AGENTS.md) y protocolos en [docs/agents/](docs/agents/).

## Estado y roadmap

- Dónde estamos y qué sigue: [docs/STATUS.md](docs/STATUS.md) · tablero: [docs/BACKLOG.md](docs/BACKLOG.md)
- Fases: [docs/ROADMAP.md](docs/ROADMAP.md) · última auditoría: [docs/audits/](docs/audits/)

### Documentación original (Google Drive)

[Arquitectura general](https://drive.google.com/file/d/1zQ8NieomysZ93aVFemlpWYM69nlgePJT/view?usp=drive_link) · [Diagrama entidad-relación](https://drive.google.com/file/d/10Vfso0BiZXnSL7fGPK4ZY2lcTmVDZOWj/view?usp=drive_link) · [Descripción del DER](https://drive.google.com/file/d/1I8oiymYeyrsXzVumH-NPndcewU9FqR-b/view?usp=drive_link) · [Documentación de pruebas](https://drive.google.com/file/d/1gnGb8l2PORtsXUj2cwMUJ1-Vwmvxj4XV/view?usp=drive_link) · [Requisitos de la prueba técnica](https://drive.google.com/file/d/1UnJ5Uj6W3mnruca8RNXeZwXMtxxrUNof/view?usp=drive_link) · [Colección de Postman](https://drive.google.com/file/d/13_zXjwXfnk0SpsFkCVjTiWvhDfEmaFze/view?usp=drive_link)

## Licencia y autor

Licencia: pendiente de definir por el autor (ver [STATUS](docs/STATUS.md)). Desarrollado por **Matias Nicolas Acevedo** — 2025.
