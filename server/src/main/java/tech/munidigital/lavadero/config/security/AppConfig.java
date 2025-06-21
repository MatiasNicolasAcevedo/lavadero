package tech.munidigital.lavadero.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import tech.munidigital.lavadero.repository.UserRepository;

/**
 * Configuración central de componentes de seguridad:
 * <ul>
 *   <li>Fuente de usuarios ({@link UserDetailsService}).</li>
 *   <li>Proveedor de autenticación basado en DAO.</li>
 *   <li>Codificador de contraseñas.</li>
 *   <li>Gestor de autenticación.</li>
 * </ul>
 */
@Configuration
@RequiredArgsConstructor
public class AppConfig {

  private final UserRepository userRepository;

  /**
   * Carga los detalles de usuario desde la base de datos por e-mail.
   *
   * @return implementación de {@link UserDetailsService}.
   * @throws ResponseStatusException 404 si el usuario no existe.
   */
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findUserByEmail(username)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "User does not exist")
        );
  }

  /**
   * Proveedor de autenticación que delega en {@link UserDetailsService}
   * y valida la contraseña con un {@link PasswordEncoder}.
   *
   * @return instancia de {@link DaoAuthenticationProvider}.
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  /**
   * Codificador de contraseñas usando BCrypt (10 rounds por defecto).
   *
   * @return {@link PasswordEncoder} basado en BCrypt.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Exposición del {@link AuthenticationManager} configurado por Spring,
   * necesario para flujos de autenticación como login con username/password.
   *
   * @param authenticationConfiguration configuración autoinyectada.
   * @return instancia del {@link AuthenticationManager}.
   * @throws Exception si la configuración falla.
   */
  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

}