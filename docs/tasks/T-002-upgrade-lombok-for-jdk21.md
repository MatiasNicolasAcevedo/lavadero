# T-002 · Actualizar Lombok para compilar con JDK 21
- Estado: Lista · Prioridad: P0 · Esfuerzo: S · Agente: 🤖 ligero
- Fase: P0 Estabilizar
- Rama: `build/T-002-upgrade-lombok-for-jdk21` · Depende de: — · Spec/ADR: [0002](../adr/0002-spring-boot-upgrade-path.md)
- Revisa: ligero

## Por qué
Con JDK 21 el build falla: `Fatal error compiling: java.lang.NoSuchFieldError: ... JCTree$JCImport ... qualid` (auditoría §3). Lombok 1.18.26 (`server/pom.xml:67` y `:148`) no soporta javac 21; la última es 1.18.48 (Maven Central, 2026-10-10).

## Objetivo
`./mvnw -B clean verify` pasa igual con JDK 17 y con JDK 21. El proyecto sigue apuntando a Java 17.

## Alcance
- Incluye: versión de Lombok en la dependencia y en `annotationProcessorPaths`; quitar el aviso "JDK 17 hasta T-002" de docs y `scripts/verify.sh`.
- **No incluye:** cambiar `java.version` ni `source/target` (siguen en 17), Spring Boot, MapStruct u otras dependencias, el Dockerfile.

## Leer antes
- `server/pom.xml` L63–68 y L137–157
- `scripts/verify.sh` (buscar `T-002`)

## Archivos previstos
- modificar: `server/pom.xml`, `scripts/verify.sh`, `.sdkmanrc` (solo el comentario), `README.md` (tabla de prerrequisitos y fila de "Problemas frecuentes"), `AGENTS.md` (Stack y Gotchas)

## Tests
- Sin tests nuevos: es un cambio de build. Los 17 existentes deben pasar con ambos JDK.

## Pasos sugeridos
1. Definir `<lombok.version>1.18.48</lombok.version>` en `<properties>` y usar `${lombok.version}` en los dos lugares.
2. Con JDK 21: `cd server && ./mvnw -B clean verify` (con la base levantada).
3. Con JDK 17 (`JAVA_HOME=<jdk17>`): lo mismo.
4. Actualizar docs: JDK "17 o 21"; borrar el gotcha de JDK 21 y la fila `NoSuchFieldError` del README; en `verify.sh` borrar la línea del aviso.

## Criterios de aceptación
- [ ] JDK 21: `./scripts/verify.sh --server` → `server: ✅ build ✅ tests (17) [JDK 21]`
- [ ] JDK 17: lo mismo con `[JDK 17]`
- [ ] `grep -n "1.18.26" server/pom.xml` → sin resultados
- [ ] `grep -rn "T-002" README.md AGENTS.md scripts/` → sin resultados
- [ ] El diff de `pom.xml` solo toca Lombok

## Notas de implementación
- Hecho: … · Pendiente: … · Próximo paso: …

## Resultado
- PR: #… · Qué cambió: … · Tests agregados: — · Pendientes → nuevas tareas: …
