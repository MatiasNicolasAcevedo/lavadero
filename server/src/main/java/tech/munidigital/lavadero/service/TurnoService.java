package tech.munidigital.lavadero.service;

import tech.munidigital.lavadero.dto.request.TurnoRequestDTO;
import tech.munidigital.lavadero.dto.response.TurnoResponseDTO;
import tech.munidigital.lavadero.entity.enums.EstadoTurno;
import java.util.List;

public interface TurnoService {

  TurnoResponseDTO createTurno(TurnoRequestDTO turnoRequestDTO);

  TurnoResponseDTO getTurnoById(Long id);

  List<TurnoResponseDTO> getTurnosByVehiculoId(Long vehiculoId);

  TurnoResponseDTO updateTurnoEstado(Long id, EstadoTurno nuevoEstado);

}
