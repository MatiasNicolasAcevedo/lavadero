package tech.munidigital.lavadero.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import tech.munidigital.lavadero.config.jwt.JwtService;
import tech.munidigital.lavadero.dto.request.LoginRequestDTO;
import tech.munidigital.lavadero.dto.response.LoginResponseDTO;
import tech.munidigital.lavadero.entity.User;
import tech.munidigital.lavadero.exception.MyException;
import tech.munidigital.lavadero.repository.UserRepository;
import tech.munidigital.lavadero.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDTO.getEmail(),
                            requestDTO.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new MyException("Bad credentials for email: " + requestDTO.getEmail(), e);
        }

        // Busca el usuario; si no se encuentra, lanza MyException
        User user = userRepository.findUserByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new MyException("User not found"));

        var jwtToken = jwtService.generateToken(user);

        return LoginResponseDTO.builder()
                .email(user.getEmail())
                .id(user.getId())
                .token(jwtToken)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

}
