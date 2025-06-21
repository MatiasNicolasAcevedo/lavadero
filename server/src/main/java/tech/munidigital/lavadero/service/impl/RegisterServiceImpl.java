package tech.munidigital.lavadero.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import tech.munidigital.lavadero.config.jwt.JwtService;
import tech.munidigital.lavadero.dto.request.RegisterRequestDTO;
import tech.munidigital.lavadero.dto.response.RegisterResponseDTO;
import tech.munidigital.lavadero.entity.User;
import tech.munidigital.lavadero.repository.UserRepository;
import tech.munidigital.lavadero.service.RegisterService;

/**
 * Implementación de {@link RegisterService} responsable de:
 * <ul>
 *   <li>Validar los datos de registro del usuario.</li>
 *   <li>Evitar duplicidad de e-mails.</li>
 *   <li>Persistir el nuevo usuario con contraseña cifrada.</li>
 *   <li>Emitir un token JWT de ingreso inmediato.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  /**
   * Registra un nuevo usuario en el sistema.
   *
   * @param requestDTO datos del usuario (nombre, e-mail, contraseña, teléfono).
   * @return DTO con la información del usuario creado y el JWT asignado.
   * @throws ResponseStatusException 400 si el e-mail ya existe o los campos son inválidos.
   */
  @Override
  public RegisterResponseDTO register(RegisterRequestDTO requestDTO) {

    // 1) Validar campos obligatorios
    validateFields(requestDTO);

    // 2) Verificar unicidad de e-mail
    if (userRepository.existsByEmail(requestDTO.getEmail())) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "There is already a user registered with the email: " + requestDTO.getEmail()
      );
    }

    // 3) Crear entidad usuario con password encriptada
    User user = User.builder()
        .firstName(requestDTO.getFirstName())
        .lastName(requestDTO.getLastName())
        .email(requestDTO.getEmail())
        .phone(requestDTO.getPhone())
        .password(passwordEncoder.encode(requestDTO.getPassword()))
        .build();

    userRepository.save(user);

    // 4) Generar JWT
    String jwtToken = jwtService.generateToken(user);

    // 5) Construir respuesta
    return RegisterResponseDTO.builder()
        .id(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .phone(user.getPhone())
        .token(jwtToken)
        .build();
  }

  /**
   * Valida que los campos esenciales no sean nulos ni vacíos.
   *
   * @param requestDTO objeto con los datos de registro.
   * @throws ResponseStatusException 400 si algún campo obligatorio está vacío.
   */
  private static void validateFields(RegisterRequestDTO requestDTO) {
    if (!StringUtils.hasText(requestDTO.getEmail())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email can't be null or empty.");
    }
    if (!StringUtils.hasText(requestDTO.getPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password can't be null or empty.");
    }
    if (!StringUtils.hasText(requestDTO.getFirstName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "FirstName can't be null or empty.");
    }
    if (!StringUtils.hasText(requestDTO.getLastName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LastName can't be null or empty.");
    }
    if (!StringUtils.hasText(requestDTO.getPhone())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone can't be null or empty.");
    }
  }

}