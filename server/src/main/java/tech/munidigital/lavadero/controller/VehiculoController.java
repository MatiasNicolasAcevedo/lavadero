package tech.munidigital.lavadero.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.munidigital.lavadero.dto.request.VehiculoRequestDTO;
import tech.munidigital.lavadero.dto.response.VehiculoResponseDTO;
import tech.munidigital.lavadero.service.VehiculoService;
import java.util.List;

@Tag(
    name = "Vehículos",
    description = "Operaciones para registrar, consultar y listar vehículos de los clientes"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @Operation(
        summary = "Registrar un nuevo vehículo",
        description = "Crea un nuevo vehículo en el sistema asociado a un cliente existente. "
            + "Se deben enviar los datos del vehículo como dominio, marca, modelo, etc."
    )
    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> createVehiculo(@Valid @RequestBody VehiculoRequestDTO vehiculoRequestDTO) {
        VehiculoResponseDTO response = vehiculoService.createVehiculo(vehiculoRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Obtener vehículo por ID",
        description = "Devuelve los datos de un vehículo específico identificado por su ID único."
    )
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> getVehiculoById(@PathVariable Long id) {
        VehiculoResponseDTO response = vehiculoService.getVehiculoById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Listar vehículos por ID de cliente",
        description = "Obtiene todos los vehículos registrados en el sistema para un cliente específico."
    )
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VehiculoResponseDTO>> getVehiculosByClienteId(@PathVariable Long clienteId) {
        List<VehiculoResponseDTO> response = vehiculoService.getVehiculosByClienteId(clienteId);
        return ResponseEntity.ok(response);
    }

}