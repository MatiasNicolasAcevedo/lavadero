package tech.munidigital.lavadero.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Filtro que se ejecuta una vez por petición para:
 * <ul>
 *   <li>Extraer y validar el JWT presente en la cabecera <code>Authorization</code>.</li>
 *   <li>Crear el {@link UsernamePasswordAuthenticationToken} y colocarlo en el
 *       {@link SecurityContextHolder} si el token es válido.</li>
 * </ul>
 * Se omiten rutas de documentación Swagger para evitar procesarlas.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  /**
   * Servicio de usuarios para cargar detalles y authorities.
   */
  private final UserDetailsService userDetailsService;

  /**
   * Servicio auxiliar que genera y valida tokens JWT.
   */
  private final JwtService jwtService;

  /**
   * Intercepta la petición, valida el token y establece la autenticación
   * en el contexto de seguridad.
   *
   * @param request     petición HTTP entrante.
   * @param response    respuesta HTTP saliente.
   * @param filterChain cadena de filtros a continuar.
   * @throws ServletException si ocurre un error en la cadena de filtros.
   * @throws IOException      si ocurre un error de I/O.
   */
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    // Se omite filtrado para Swagger UI y documentación OpenAPI
    String path = request.getServletPath();
    if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
      filterChain.doFilter(request, response);
      return;
    }

    // Se verifica la existencia y formato de la cabecera Authorization
    final String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    // Se extrae el JWT
    String jwt = authHeader.substring(7);
    String userEmail = jwtService.getUserName(jwt);

    // Si el usuario no está autenticado aún y el token es válido → autenticar
    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
      if (jwtService.validateToken(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
            );
        authenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }

    // Continuar la cadena de filtros
    filterChain.doFilter(request, response);
  }

}