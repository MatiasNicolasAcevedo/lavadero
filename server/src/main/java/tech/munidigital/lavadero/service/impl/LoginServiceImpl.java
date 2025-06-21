package tech.munidigital.lavadero.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.munidigital.lavadero.config.jwt.JwtService;
import tech.munidigital.lavadero.dto.request.LoginRequestDTO;
import tech.munidigital.lavadero.dto.response.LoginResponseDTO;
import tech.munidigital.lavadero.entity.User;
import tech.munidigital.lavadero.repository.UserRepository;
import tech.munidigital.lavadero.service.LoginService;

/**
 * Implementación de {@link LoginService} encargada de autenticar al usuario
 * y emitir un token JWT válido para las siguientes peticiones.
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  /**
   * Autentica las credenciales recibidas y genera un JWT de sesión.
   * <ol>
   *   <li>Realiza la autenticación con {@link AuthenticationManager}.</li>
   *   <li>Si las credenciales son inválidas lanza 401 (UNAUTHORIZED).</li>
   *   <li>Busca el usuario en la base; si no existe devuelve 404.</li>
   *   <li>Emite un JWT con los datos del usuario.</li>
   * </ol>
   *
   * @param requestDTO DTO con <code>email</code> y <code>password</code>.
   * @return DTO con la info del usuario y el token generado.
   * @throws ResponseStatusException 401 si las credenciales son incorrectas.
   * @throws ResponseStatusException 404 si el usuario no existe.
   */
  @Override
  public LoginResponseDTO login(LoginRequestDTO requestDTO) {

    // 1) Autenticación con Spring Security
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              requestDTO.getEmail(),
              requestDTO.getPassword()
          )
      );
    } catch (BadCredentialsException e) {
      throw new ResponseStatusException(
          HttpStatus.UNAUTHORIZED,
          "Bad credentials for email: " + requestDTO.getEmail(),
          e
      );
    }

    // 2) Recuperar usuario de la base
    User user = userRepository.findUserByEmail(requestDTO.getEmail())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "User not found"));

    // 3) Generar token JWT
    String jwtToken = jwtService.generateToken(user);

    // 4) Construir respuesta
    return LoginResponseDTO.builder()
        .id(user.getId())
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .token(jwtToken)
        .build();
  }

}