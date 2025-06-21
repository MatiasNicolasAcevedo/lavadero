package tech.munidigital.lavadero.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración global de CORS para el backend.
 *
 * <p>Permite que cualquier origen (`*`) consuma la API utilizando los métodos
 * HTTP más comunes. Adecuado para entornos de desarrollo o APIs públicas;
 * en producción se recomienda restringir <code>allowedOrigins</code> a los
 * dominios confiables.</p>
 */
@Configuration
public class CorsConfig {

  /**
   * Define un {@link WebMvcConfigurer} que personaliza las reglas CORS
   * de la aplicación.
   *
   * @return instancia de {@link WebMvcConfigurer} con mapeo CORS global.
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {

      /**
       * Aplica la política CORS a todos los endpoints (<code>/**</code>):
       * <ul>
       *   <li><strong>allowedOrigins</strong>: <code>*</code> — cualquier dominio.</li>
       *   <li><strong>allowedMethods</strong>: GET, POST, PUT, PATCH, DELETE, OPTIONS.</li>
       *   <li><strong>allowedHeaders</strong>: <code>*</code> — todos los encabezados.</li>
       * </ul>
       *
       * @param registry registro donde se añaden las configuraciones CORS.
       */
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*");
      }
    };
  }

}