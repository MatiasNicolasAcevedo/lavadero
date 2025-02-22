package tech.munidigital.lavadero.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tech.munidigital.lavadero.config.jwt.JwtService;
import tech.munidigital.lavadero.dto.request.RegisterRequestDTO;
import tech.munidigital.lavadero.dto.response.RegisterResponseDTO;
import tech.munidigital.lavadero.entity.User;
import tech.munidigital.lavadero.exception.MyException;
import tech.munidigital.lavadero.repository.UserRepository;
import tech.munidigital.lavadero.service.RegisterService;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO requestDTO) {
        // Validar que los campos obligatorios tengan contenido
        validateFields(requestDTO);

        // Verificar si el email ya se encuentra registrado
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new MyException("There is already a user registered with the email: " + requestDTO.getEmail());
        }

        // Crear el usuario y encriptar la contraseña
        User user = User.builder()
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .firstName(requestDTO.getFirstName())
                .lastName(requestDTO.getLastName())
                .phone(requestDTO.getPhone())
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return RegisterResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .token(jwtToken)
                .build();
    }

    private static void validateFields(RegisterRequestDTO requestDTO) {
        if (!StringUtils.hasText(requestDTO.getEmail())) {
            throw new MyException("Email can't be null or empty.");
        }
        if (!StringUtils.hasText(requestDTO.getPassword())) {
            throw new MyException("Password can't be null or empty.");
        }
        if (!StringUtils.hasText(requestDTO.getFirstName())) {
            throw new MyException("FirstName can't be null or empty.");
        }
        if (!StringUtils.hasText(requestDTO.getLastName())) {
            throw new MyException("LastName can't be null or empty.");
        }
        if (!StringUtils.hasText(requestDTO.getPhone())) {
            throw new MyException("Phone can't be null or empty.");
        }
    }

}