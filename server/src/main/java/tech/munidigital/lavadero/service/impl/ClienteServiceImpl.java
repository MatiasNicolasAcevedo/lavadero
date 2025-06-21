package tech.munidigital.lavadero.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.munidigital.lavadero.dto.request.ClienteRequestDTO;
import tech.munidigital.lavadero.dto.response.ClienteResponseDTO;
import tech.munidigital.lavadero.entity.Cliente;
import tech.munidigital.lavadero.mappers.ClienteMapper;
import tech.munidigital.lavadero.repository.ClienteRepository;
import tech.munidigital.lavadero.service.ClienteService;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de {@link ClienteService} que gestiona la lógica de negocio
 * relacionada con los clientes (alta, consulta y listado).
 */
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

  private final ClienteRepository clienteRepository;
  private final ClienteMapper clienteMapper;

  /**
   * Crea un nuevo cliente verificando previamente que el correo no exista.
   *
   * @param clienteRequestDTO datos del cliente a registrar.
   * @return representación DTO del cliente persistido.
   * @throws ResponseStatusException 400 si el correo ya está registrado.
   */
  @Override
  public ClienteResponseDTO createCliente(ClienteRequestDTO clienteRequestDTO) {
    // Validar si ya existe un cliente con el mismo correo electrónico
    clienteRepository.findByCorreoElectronico(clienteRequestDTO.getCorreoElectronico())
        .ifPresent(c -> {
          throw new ResponseStatusException(
              HttpStatus.BAD_REQUEST, "El correo electrónico ya está registrado.");
        });

    Cliente savedCliente = clienteRepository.save(
        clienteMapper.toEntity(clienteRequestDTO)
    );

    return clienteMapper.toDto(savedCliente);
  }

  /**
   * Recupera un cliente por su identificador.
   *
   * @param id PK del cliente.
   * @return DTO con los datos del cliente.
   * @throws ResponseStatusException 404 si no existe el cliente.
   */
  @Override
  public ClienteResponseDTO getClienteById(Long id) {
    Cliente cliente = clienteRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Cliente no encontrado con id: " + id));

    return clienteMapper.toDto(cliente);
  }

  /**
   * Obtiene todos los clientes registrados en el sistema.
   *
   * @return lista de clientes en formato DTO.
   */
  @Override
  public List<ClienteResponseDTO> getAllClientes() {
    return clienteRepository.findAll()
        .stream()
        .map(clienteMapper::toDto)
        .collect(Collectors.toList());
  }

}
