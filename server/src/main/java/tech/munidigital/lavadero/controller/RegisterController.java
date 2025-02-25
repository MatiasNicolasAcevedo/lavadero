package tech.munidigital.lavadero.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.munidigital.lavadero.dto.request.RegisterRequestDTO;
import tech.munidigital.lavadero.dto.response.RegisterResponseDTO;
import tech.munidigital.lavadero.service.RegisterService;

@RestController
@RequestMapping("v1/api/auth/register")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO requestDTO) {
        RegisterResponseDTO registerResponseDTO = registerService.register(requestDTO);
        return ResponseEntity.ok(registerResponseDTO);
    }

}
