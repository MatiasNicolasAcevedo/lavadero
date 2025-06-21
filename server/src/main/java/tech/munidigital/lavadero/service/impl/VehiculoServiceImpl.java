package tech.munidigital.lavadero.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.munidigital.lavadero.dto.request.VehiculoRequestDTO;
import tech.munidigital.lavadero.dto.response.VehiculoResponseDTO;
import tech.munidigital.lavadero.entity.Vehiculo;
import tech.munidigital.lavadero.mappers.VehiculoMapper;
import tech.munidigital.lavadero.repository.ClienteRepository;
import tech.munidigital.lavadero.repository.VehiculoRepository;
import tech.munidigital.lavadero.service.VehiculoService;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de {@link VehiculoService} encargada del
 * alta y la consulta de vehículos vinculados a clientes.
 */
@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements VehiculoService {

  private final VehiculoRepository vehiculoRepository;
  private final ClienteRepository clienteRepository;
  private final VehiculoMapper vehiculoMapper;

  /**
   * Registra un nuevo vehículo para un cliente dado.
   *
   * @param vehiculoRequestDTO datos del vehículo a crear.
   * @return DTO del vehículo persistido.
   * @throws ResponseStatusException 404 si el cliente no existe.
   */
  @Override
  public VehiculoResponseDTO createVehiculo(VehiculoRequestDTO vehiculoRequestDTO) {

    // 1) Verificar existencia del cliente
    var cliente = clienteRepository.findById(vehiculoRequestDTO.getClienteId())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Cliente no encontrado con id: " + vehiculoRequestDTO.getClienteId()
        ));

    // 2) Mapear y asociar cliente
    Vehiculo vehiculo = vehiculoMapper.toEntity(vehiculoRequestDTO);
    vehiculo.setCliente(cliente);

    // 3) Persistir y devolver DTO
    Vehiculo savedVehiculo = vehiculoRepository.save(vehiculo);
    return vehiculoMapper.toDto(savedVehiculo);
  }

  /**
   * Obtiene un vehículo por su ID.
   *
   * @param id PK del vehículo.
   * @return DTO con la información del vehículo.
   * @throws ResponseStatusException 404 si no existe el vehículo.
   */
  @Override
  public VehiculoResponseDTO getVehiculoById(Long id) {
    Vehiculo vehiculo = vehiculoRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Vehículo no encontrado con id: " + id
        ));
    return vehiculoMapper.toDto(vehiculo);
  }

  /**
   * Lista todos los vehículos asociados a un cliente.
   *
   * @param clienteId PK del cliente.
   * @return lista de vehículos en formato DTO.
   */
  @Override
  public List<VehiculoResponseDTO> getVehiculosByClienteId(Long clienteId) {
    return vehiculoRepository.findByClienteId(clienteId)
        .stream()
        .map(vehiculoMapper::toDto)
        .collect(Collectors.toList());
  }

}