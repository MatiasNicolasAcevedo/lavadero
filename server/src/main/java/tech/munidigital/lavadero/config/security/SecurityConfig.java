package tech.munidigital.lavadero.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import tech.munidigital.lavadero.config.jwt.JwtFilter;

/**
 * Configura las políticas de seguridad HTTP:
 * <ul>
 *   <li>Deshabilita CSRF (API REST stateless).</li>
 *   <li>Define CORS por defecto.</li>
 *   <li>Permite el acceso anónimo a endpoints públicos (auth, health, swagger).</li>
 *   <li>Exige autenticación JWT para el resto de rutas.</li>
 *   <li>Establece la sesión como <i>STATELESS</i>.</li>
 *   <li>Registra el {@link JwtFilter} antes del filtro de autenticación por usuario/contraseña.</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  /**
   * Proveedor de autenticación configurado con {@link AppConfig}.
   */
  private final AuthenticationProvider authenticationProvider;

  /**
   * Filtro JWT que valida tokens y coloca la autenticación en el contexto.
   */
  private final JwtFilter jwtFilter;

  /**
   * Cadena de filtros principal que gestiona la seguridad HTTP.
   *
   * @param httpSecurity instancia de {@link HttpSecurity} inyectada por Spring.
   * @return {@link SecurityFilterChain} completamente construida.
   * @throws Exception si la construcción de la cadena falla.
   */
  @Bean
  public SecurityFilterChain securiityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        // La API es stateless: CSRF deshabilitado
        .csrf(AbstractHttpConfigurer::disable)
        // CORS con configuración por defecto
        .cors(Customizer.withDefaults())
        // Reglas de autorización
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(publicEndpoints()).permitAll()   // endpoints públicos
            .anyRequest().authenticated()                    // resto autenticados
        )
        // Política de sesiones (sin estado)
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // Proveedor personalizado (BCrypt + DB)
        .authenticationProvider(authenticationProvider)
        // Insertamos el filtro JWT antes del UsernamePasswordAuthenticationFilter
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

  /**
   * Agrupa los <i>request matchers</i> que NO requieren autenticación.
   *
   * @return {@link RequestMatcher} compuesto con las rutas públicas.
   */
  private RequestMatcher publicEndpoints() {
    return new OrRequestMatcher(
        new AntPathRequestMatcher("/v1/api/auth/*"),     // login & register
        new AntPathRequestMatcher("/swagger-ui/**"),     // documentación Swagger
        new AntPathRequestMatcher("/v3/api-docs/**"),    // OpenAPI JSON/YAML
        new AntPathRequestMatcher("/v1/api/render/*")    // health-check
    );
  }

}