# 0002 · Camino de actualización: Java 17→21 y Spring Boot 3.3 → 3.5 → 4.x

- Estado: Propuesta (se acepta al empezar la fase P1) · Fecha: 2026-10-10

## Contexto

El back usa Spring Boot 3.3.1 (sin soporte OSS), Java 17 y Lombok 1.18.26, que no compila con JDK 21 (auditoría 2026-10-10 §3). Versiones vigentes verificadas en Maven Central: Boot 3.5.16 (última 3.x) y 4.1.1; springdoc 2.8.17 / 3.1.1. Boot 4 trae Spring Framework 7, Spring Security 7 (elimina `AntPathRequestMatcher`, usado en `SecurityConfig.java:83-86`), Jackson 3 y Jakarta EE 11.

## Decisión

1. P0: solo Lombok (T-002) para compilar con JDK 17 y 21, sin cambiar `java.version`.
2. P1, cada paso en su PR y protegido por la red de tests de P0 (T-006, T-008, T-009, T-012, T-013):
   1. Java 21 (`java.version`, Dockerfile, `.sdkmanrc`, CI).
   2. Boot 3.3 → 3.5.x con OpenRewrite `org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_5` y resolver deprecaciones.
   3. Boot 3.5 → 4.x con OpenRewrite (`UpgradeSpringBoot_4_0` o la receta vigente), springdoc 3.x, jjwt 0.13.
3. Cada salto: `verify.sh` + E2E en verde antes y después; nada de cambios funcionales en el mismo PR.

## Alternativas

- Saltar directo 3.3 → 4.x: un diff enorme, imposible de revisar y difícil de bisectar.
- Quedarse en 3.3: sin parches de seguridad.
- Java 25 en lugar de 21: viable; se decide en la tarea de Java según soporte de Lombok/MapStruct en ese momento.

## Consecuencias

- 3 PRs 🧠 en P1, más uno por dependencias acompañantes. Ningún PR de migración se mezcla con features.
- El Dockerfile pasa a `eclipse-temurin` en el paso de Java 21.
