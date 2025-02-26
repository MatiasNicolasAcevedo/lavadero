# Sistema de Gestión de Lavadero de Autos 🚗✨

Bienvenido al repositorio de mi proyecto **Lavadero**. Este sistema te permite gestionar clientes, vehículos, turnos y cobros para un lavadero de autos, desarrollándolo con **Java**, **Spring Boot** y **PostgreSQL**.

[![Landing Page](https://github.com/user-attachments/assets/ef782d5a-d3a4-4d54-ad76-e61f5cc63329)](https://lavadero-munidigital.vercel.app)

_¡Haz click en la imagen para ver el deploy del frontend!_

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

- <a href="https://drive.google.com/file/d/1Hvd1YeR2u5PsGlAbFjLXs15oGK90-_Sp/view?usp=drive_link" target="_blank">env.properties - Abrir URL</a> 🔧  
  _Archivo de configuración con las propiedades de entorno (credenciales y variables de prueba)._

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

## 🚀 Cómo Ejecutar el Proyecto

1. Cloná el repositorio:
   ```bash
   git clone https://github.com/MatiasNicolasAcevedo/lavadero.git

2. Navegá hasta el directorio del proyecto:
   ```bash
   cd lavadero

3. Configurá las propiedades en el archivo env.properties según tus necesidades.

4. Ejecutá el proyecto:
   ```bash
   mvn spring-boot:run

---

## 🧪 Pruebas de Integración
El proyecto incluye una suite completa de pruebas de integración ubicadas en el package test. Estas pruebas reflejan la misma arquitectura del proyecto y validan los principales flujos de negocio, asegurando el correcto funcionamiento de los endpoints y la lógica de negocio.

---

## ✨ Desarrollado por Matias Nicolas Acevedo - 2025
