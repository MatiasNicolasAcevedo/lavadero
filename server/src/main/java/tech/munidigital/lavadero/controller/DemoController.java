package tech.munidigital.lavadero.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
    name = "Demo",
    description = "Endpoint de demostración para verificar el acceso a endpoints seguros"
)
@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class DemoController {

    @Operation(
        summary = "Endpoint seguro de prueba",
        description = "Este endpoint está protegido por seguridad y se utiliza para validar que la autenticación y autorización funcionan correctamente."
    )
    @PostMapping(value = "/demo")
    public String welcome() {
        return "Welcome from a secure endpoint";
    }

}
