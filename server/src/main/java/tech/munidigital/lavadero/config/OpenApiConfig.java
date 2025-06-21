package tech.munidigital.lavadero.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración central de la especificación OpenAPI/Swagger para el proyecto.
 *
 * <p>Define:</p>
 * <ul>
 *   <li><strong>Info</strong>: título, versión, descripción, contacto y licencia.</li>
 *   <li><strong>Servers</strong>: URL y descripción de los entornos (local y producción).</li>
 *   <li><strong>SecurityRequirement</strong>: requiere esquema <code>bearerAuth</code> por defecto.</li>
 *   <li><strong>SecurityScheme</strong>: esquema HTTP <em>Bearer</em> con formato JWT.</li>
 * </ul>
 *
 * <p>Al estar marcada como <code>@Configuration</code>, Spring la detecta y
 * <em>springdoc-openapi</em> la incorpora automáticamente al generar la
 * documentación Swagger UI.</p>
 */
@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "MuniDigital - Lavadero",
            email = "matias.nicolas.acevedo@gmail.com",
            url = "https://lavadero-munidigital.vercel.app/"
        ),
        description = "Prueba Técnica Java",
        title = "OpenApi specification - MuniDigital",
        version = "1.0",
        license = @License(
            name = "Licence name",
            url = "https://springdoc.org"
        ),
        termsOfService = "Terms of service"
    ),
    servers = {
        @Server(description = "Local ENV", url = "http://localhost:8080"),
        @Server(description = "PROD ENV",  url = "https://lavaderoweb.onrender.com")
    },
    security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT Bearer token authentication",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiConfig {
  // Solo actúa como portador de anotaciones; no contiene lógica de negocio.
}