package tech.munidigital.lavadero.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.munidigital.lavadero.dto.request.ClienteRequestDTO;
import tech.munidigital.lavadero.dto.response.ClienteResponseDTO;
import tech.munidigital.lavadero.service.ClienteService;
import java.util.List;

@RestController
@RequestMapping("/v1/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Crear un cliente
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> createCliente(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO response = clienteService.createCliente(clienteRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Obtener un cliente por id
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> getClienteById(@PathVariable Long id) {
        ClienteResponseDTO response = clienteService.getClienteById(id);
        return ResponseEntity.ok(response);
    }

    // Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> getAllClientes() {
        List<ClienteResponseDTO> response = clienteService.getAllClientes();
        return ResponseEntity.ok(response);
    }

}