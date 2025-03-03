package tech.munidigital.lavadero.service.impl;

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

@Service
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public RegisterServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO requestDTO) {
        validateFields(requestDTO);

        // Verificar si el email ya se encuentra registrado
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "There is already a user registered with the email: " + requestDTO.getEmail()
            );
        }

        // Crear el usuario y encriptar la contraseña
        User user = User.builder()
                .firstName(requestDTO.getFirstName())
                .lastName(requestDTO.getLastName())
                .email(requestDTO.getEmail())
                .phone(requestDTO.getPhone())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
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