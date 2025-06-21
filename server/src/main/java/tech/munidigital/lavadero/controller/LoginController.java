package tech.munidigital.lavadero.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.munidigital.lavadero.dto.request.LoginRequestDTO;
import tech.munidigital.lavadero.dto.response.LoginResponseDTO;
import tech.munidigital.lavadero.service.LoginService;

@Tag(
    name = "Autenticación",
    description = "Operaciones relacionadas con el inicio de sesión de usuarios"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/auth/login")
public class LoginController {

    private final LoginService loginService;

    @Operation(
        summary = "Iniciar sesión",
        description = "Permite a un usuario autenticarse en el sistema enviando credenciales válidas. "
            + "Retorna un token JWT en el encabezado `Authorization` y en el cuerpo de la respuesta."
    )
    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        LoginResponseDTO loginResponseDTO = loginService.login(requestDTO);
        return ResponseEntity.ok()
            .header("Authorization", "Bearer " + loginResponseDTO.getToken())
            .body(loginResponseDTO);
    }

}