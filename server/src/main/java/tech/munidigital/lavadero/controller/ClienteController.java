package tech.munidigital.lavadero.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.munidigital.lavadero.dto.request.ClienteRequestDTO;
import tech.munidigital.lavadero.dto.response.ClienteResponseDTO;
import tech.munidigital.lavadero.service.ClienteService;
import java.util.List;

@Tag(
    name = "Clientes",
    description = "Operaciones relacionadas con la gestión de clientes del sistema Lavadero"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/clientes")
public class ClienteController {

  private final ClienteService clienteService;

  @Operation(
      summary = "Crear un nuevo cliente",
      description = "Registra un nuevo cliente en el sistema con los datos proporcionados en el cuerpo de la solicitud."
  )
  @PostMapping
  public ResponseEntity<ClienteResponseDTO> createCliente(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
    ClienteResponseDTO response = clienteService.createCliente(clienteRequestDTO);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Operation(
      summary = "Obtener cliente por ID",
      description = "Devuelve los datos de un cliente específico según su ID. Lanza excepción si el cliente no existe."
  )
  @GetMapping("/{id}")
  public ResponseEntity<ClienteResponseDTO> getClienteById(@PathVariable Long id) {
    ClienteResponseDTO response = clienteService.getClienteById(id);
    return ResponseEntity.ok(response);
  }

  @Operation(
      summary = "Listar todos los clientes",
      description = "Obtiene una lista de todos los clientes registrados en el sistema."
  )
  @GetMapping
  public ResponseEntity<List<ClienteResponseDTO>> getAllClientes() {
    List<ClienteResponseDTO> response = clienteService.getAllClientes();
    return ResponseEntity.ok(response);
  }

}