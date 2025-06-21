package tech.munidigital.lavadero.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
    name = "Health Check",
    description = "Endpoint de monitoreo para verificar que el backend esté disponible"
)
@RestController
@RequestMapping("v1/api/render")

public class HealthCheckController {

    @Operation(
        summary = "Verificar el estado del servidor",
        description = "Endpoint público utilizado para monitorear si el servidor está activo. "
            + "Ideal para chequeos automáticos de salud en entornos como Render, Docker, etc."
    )
    @GetMapping("/health")
    public String checkHealth() {
        return "Server is awake and running!";
    }

}