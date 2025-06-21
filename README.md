# Sistema de Gestión de Lavadero de Autos 🚗✨

Bienvenido al repositorio de mi proyecto **Lavadero**. Este sistema te permite gestionar clientes, vehículos, turnos y cobros para un lavadero de autos, desarrollándolo con **Java**, **Spring Boot** y **PostgreSQL**.

[![Landing Page](https://github.com/user-attachments/assets/ef782d5a-d3a4-4d54-ad76-e61f5cc63329)](https://lavadero-munidigital.vercel.app)

_¡Haz click en la imagen para ver el deploy del frontend!_

> [!IMPORTANT]
> Cuenta demo para iniciar sesión:<br>
> Email: lavadero@munidigital.com<br>
> Contraseña: 12345Aa*




---

## Playlist del Proyecto  
[![Ver Playlist en YouTube](https://img.shields.io/badge/YouTube-Playlist-red?logo=youtube)](https://youtube.com/playlist?list=PLkNS1qPGkDME9XgneSvzi2yYZ_AQmUOdF&si=nE-MgutukRJn5So_)  

_Explora la implementación completa del sistema de gestión de lavadero de autos, con backend en Java Spring Boot y frontend conectado a una base de datos real._

---

- **Swagger Documentation:**  
  [Ver Documentación Swagger](https://lavaderoweb.onrender.com/swagger-ui/index.html#/) 📝  
  _Explora y prueba los endpoints de la API de forma interactiva._
  
---

## 📚 Documentación del Proyecto

Encontrarás a continuación los archivos de documentación que he generado durante el desarrollo del proyecto:

- <a href="https://drive.google.com/file/d/1zQ8NieomysZ93aVFemlpWYM69nlgePJT/view?usp=drive_link" target="_blank">Arquitectura General del Proyecto.pdf - Abrir URL</a> 🏗️  
  _Descripción general de la estructura y organización del proyecto._

- <a href="https://drive.google.com/file/d/10Vfso0BiZXnSL7fGPK4ZY2lcTmVDZOWj/view?usp=drive_link" target="_blank">Diagrama Entidad Relacion</a> 🖼️  
  _Imagen del diagrama que muestra las relaciones entre las entidades del sistema._

- <a href="https://drive.google.com/file/d/1I8oiymYeyrsXzVumH-NPndcewU9FqR-b/view?usp=drive_link" target="_blank">Descripción DER.pdf - Abrir URL</a> 📑  
  _Explicación detallada del Diagrama de Entidad-Relación (DER) con las entidades y relaciones definidas._

- <a href="https://drive.google.com/file/d/1gnGb8l2PORtsXUj2cwMUJ1-Vwmvxj4XV/view?usp=drive_link" target="_blank">Documentación de Pruebas.pdf - Abrir URL</a> 🧪  
  _Resumen y descripción de las pruebas de integración implementadas para validar la funcionalidad del sistema._

- <a href="https://drive.google.com/file/d/1UnJ5Uj6W3mnruca8RNXeZwXMtxxrUNof/view?usp=drive_link" target="_blank">Requisitos de la Prueba Técnica.pdf - Abrir URL</a> 📋  
  _Documento con los requerimientos y especificaciones de la prueba técnica._

- <a href="https://drive.google.com/file/d/13_zXjwXfnk0SpsFkCVjTiWvhDfEmaFze/view?usp=drive_link" target="_blank">Postman Collection - Abrir URL</a> 📬  
  _Archivo con la colección de endpoints de la API para pruebas en Postman._


---

## ⚙️ Arquitectura del Proyecto

El proyecto está organizado en varios paquetes que siguen una estructura limpia y modular. La arquitectura se basa en los siguientes módulos:

- **config**: Configuraciones generales (seguridad, JWT, CORS y OpenAPI).  
- **controller**: Endpoints REST para la comunicación con el frontend o consumidores de la API.  
- **dto**: Objetos de transferencia de datos (requests y responses).  
- **entity**: Entidades JPA que representan las tablas en la base de datos.  
- **enums**: Enumeraciones para valores constantes (ej: EstadoTurno, TipoServicio).  
- **exception**: Manejo de excepciones y errores globales.  
- **mappers**: Clases para la conversión entre entidades y DTOs.  
- **repository**: Interfaces de Spring Data JPA para la persistencia.  
- **service**: Lógica de negocio, implementada en servicios y sus respectivas implementaciones.  
- **util**: Clases de utilidad y helpers.

---

# 🧽 Lavadero – Backend

> Tecnologías: **Spring Boot 3 · Java 17 · PostgreSQL 16 · Docker Compose**

---

## ⚙️ Requisitos Previos

| Herramienta       | Versión mínima | Cómo chequear                              |
|-------------------|----------------|--------------------------------------------|
| **JDK**           | 17 (LTS)       | `java --version` → debería decir `17.x`    |
| **Maven**         | 3.9 o superior | `mvn -v`                                   |
| **Docker**        | 24.x           | `docker --version`                         |
| **Docker Compose**| 2.x o superior | `docker compose version`                   |

---

## 🚀 Pasos para Levantar el Proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/MatiasNicolasAcevedo/lavadero.git
```

### 2. Abrir el proyecto en IntelliJ

Abrí la carpeta `server/` con IntelliJ IDEA como un proyecto Maven.  
El IDE debería detectar automáticamente Spring y las dependencias.

---

### 3. Compilar el proyecto y bajar dependencias

```bash
# Ejecuta los tests
mvn clean install

# O si querés compilar más rápido sin ejecutar tests:
mvn clean install -DskipTests
```

---

### 4. Levantar la base de datos con Docker

El proyecto incluye un `docker-compose.yml` configurado con Postgres 16.

```bash
# Levanta el contenedor en segundo plano
docker compose up -d
```

Verificá que el contenedor esté corriendo correctamente:

```bash
docker compose ps
```

Para detener y eliminar los contenedores junto con los volúmenes de datos:

```bash
docker compose down -v
```

---

### 5. Configurar propiedades de conexión

Tenés **dos opciones** para configurar las variables de conexión a la base de datos:

#### ✅ Opción A: Usar `application.properties`

1. Editá el archivo `src/main/resources/application.properties`.
2. Comentá las líneas **6 a 12**.
3. Descomentá las líneas **27 a 34** para usar conexión local con Docker.

---

#### 🔐 Opción B: Crear un archivo `env.properties`

1. En la raíz del proyecto, creá un archivo llamado `env.properties`.
2. Agregá lo siguiente:

```properties
DB_URL=jdbc:postgresql://localhost:5432/lavadero
DB_DRIVER_CLASS_NAME=org.postgresql.Driver
DB_USER=lavadero_user
DB_PASSWORD=lavadero_pass
DB_DIALECT=org.hibernate.dialect.PostgreSQLDialect
SECRET=Mb2QzHZkybl7W6m1Rnq2u3RLHEmR89pjjCquQB1VwC64=
```

Esto permite mantener tus credenciales fuera del archivo principal del proyecto (útil para entornos CI/CD o trabajo en equipo).

---

### 6. Correr la aplicación

Ejecutá el siguiente comando para levantar el backend con hot reload:

```bash
mvn spring-boot:run
```

Una vez levantado, accedé a la API REST desde:

- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

---

Con estos pasos deberías poder levantar el proyecto en tu entorno local de forma rápida y sin complicaciones 🚀

---

## 🧪 Pruebas de Integración
El proyecto incluye una suite completa de pruebas de integración ubicadas en el package test. Estas pruebas reflejan la misma arquitectura del proyecto y validan los principales flujos de negocio, asegurando el correcto funcionamiento de los endpoints y la lógica de negocio.

---

## ✨ Desarrollado por Matias Nicolas Acevedo - 2025
