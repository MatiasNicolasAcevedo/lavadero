package tech.munidigital.lavadero.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.munidigital.lavadero.dto.request.LoginRequestDTO;
import tech.munidigital.lavadero.dto.response.LoginResponseDTO;
import tech.munidigital.lavadero.service.LoginService;

@RestController
@RequestMapping("v1/api/auth/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        LoginResponseDTO loginResponseDTO = loginService.login(requestDTO);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + loginResponseDTO.getToken())
                .body(loginResponseDTO);
    }

}