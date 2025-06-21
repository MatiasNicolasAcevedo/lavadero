package tech.munidigital.lavadero.service;

import tech.munidigital.lavadero.dto.request.ClienteRequestDTO;
import tech.munidigital.lavadero.dto.response.ClienteResponseDTO;
import java.util.List;

public interface ClienteService {

  ClienteResponseDTO createCliente(ClienteRequestDTO clienteRequestDTO);

  ClienteResponseDTO getClienteById(Long id);

  List<ClienteResponseDTO> getAllClientes();

}