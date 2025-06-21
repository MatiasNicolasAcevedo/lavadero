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
import tech.munidigital.lavadero.dto.request.RegisterRequestDTO;
import tech.munidigital.lavadero.dto.response.RegisterResponseDTO;
import tech.munidigital.lavadero.service.RegisterService;

@Tag(
    name = "Registro",
    description = "Operaciones relacionadas con la creación de nuevos usuarios en el sistema"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/auth/register")
public class RegisterController {

    private final RegisterService registerService;

    @Operation(
        summary = "Registrar un nuevo usuario",
        description = "Permite registrar un nuevo usuario en el sistema enviando los datos requeridos como nombre, correo, contraseña, etc. "
            + "Retorna una confirmación junto con datos útiles del usuario creado (como el token JWT en caso de registrarse autenticado)."
    )
    @PostMapping
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO requestDTO) {
        RegisterResponseDTO registerResponseDTO = registerService.register(requestDTO);
        return ResponseEntity.ok(registerResponseDTO);
    }

}
