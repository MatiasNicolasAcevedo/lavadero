package tech.munidigital.lavadero.service;

import tech.munidigital.lavadero.dto.request.VehiculoRequestDTO;
import tech.munidigital.lavadero.dto.response.VehiculoResponseDTO;
import java.util.List;

public interface VehiculoService {

  VehiculoResponseDTO createVehiculo(VehiculoRequestDTO vehiculoRequestDTO);

  VehiculoResponseDTO getVehiculoById(Long id);

  List<VehiculoResponseDTO> getVehiculosByClienteId(Long clienteId);

}
