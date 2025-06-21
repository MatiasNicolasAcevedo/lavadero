package tech.munidigital.lavadero.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.munidigital.lavadero.dto.request.TurnoRequestDTO;
import tech.munidigital.lavadero.dto.response.TurnoResponseDTO;
import tech.munidigital.lavadero.entity.Turno;
import tech.munidigital.lavadero.entity.Vehiculo;
import tech.munidigital.lavadero.entity.enums.EstadoTurno;
import tech.munidigital.lavadero.mappers.TurnoMapper;
import tech.munidigital.lavadero.repository.TurnoRepository;
import tech.munidigital.lavadero.repository.VehiculoRepository;
import tech.munidigital.lavadero.service.TurnoService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de {@link TurnoService} que gestiona la creación,
 * consulta y actualización de estados de turnos asociados a vehículos.
 */
@Service
@RequiredArgsConstructor
public class TurnoServiceImpl implements TurnoService {

  private final TurnoRepository turnoRepository;
  private final VehiculoRepository vehiculoRepository;
  private final TurnoMapper turnoMapper;

  /**
   * Crea un nuevo turno verificando reglas básicas:
   * <ul>
   *   <li>La fecha/hora no puede estar en el pasado.</li>
   *   <li>El vehículo debe existir.</li>
   * </ul>
   *
   * @param turnoRequestDTO datos del turno solicitado.
   * @return DTO con la información del turno persistido.
   * @throws ResponseStatusException 400 si la fecha es pasada.
   * @throws ResponseStatusException 404 si el vehículo no existe.
   */
  @Override
  public TurnoResponseDTO createTurno(TurnoRequestDTO turnoRequestDTO) {

    // 1) Validar fecha futura
    if (turnoRequestDTO.getFechaHora().isBefore(LocalDateTime.now())) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "La fecha y hora no pueden ser en el pasado");
    }

    // 2) Verificar existencia del vehículo
    Vehiculo vehiculo = vehiculoRepository.findById(turnoRequestDTO.getVehiculoId())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Vehículo no encontrado con id: " + turnoRequestDTO.getVehiculoId()
        ));

    // 3) Mapear, establecer relación y persistir
    Turno turno = turnoMapper.toEntity(turnoRequestDTO);
    turno.setVehiculo(vehiculo);

    Turno savedTurno = turnoRepository.save(turno);
    return turnoMapper.toDto(savedTurno);
  }

  /**
   * Obtiene un turno por su identificador.
   *
   * @param id PK del turno.
   * @return DTO con los datos del turno.
   * @throws ResponseStatusException 404 si el turno no existe.
   */
  @Override
  public TurnoResponseDTO getTurnoById(Long id) {
    Turno turno = turnoRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Turno no encontrado con id: " + id));

    return turnoMapper.toDto(turno);
  }

  /**
   * Lista todos los turnos asociados a un vehículo.
   *
   * @param vehiculoId PK del vehículo.
   * @return lista de turnos en formato DTO.
   */
  @Override
  public List<TurnoResponseDTO> getTurnosByVehiculoId(Long vehiculoId) {
    return turnoRepository.findByVehiculoId(vehiculoId)
        .stream()
        .map(turnoMapper::toDto)
        .collect(Collectors.toList());
  }

  /**
   * Actualiza el estado de un turno.
   *
   * @param id          PK del turno.
   * @param nuevoEstado nuevo estado a asignar.
   * @return DTO con el turno actualizado.
   * @throws ResponseStatusException 404 si el turno no existe.
   */
  @Override
  public TurnoResponseDTO updateTurnoEstado(Long id, EstadoTurno nuevoEstado) {
    Turno turno = turnoRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Turno no encontrado con id: " + id));

    turno.setEstado(nuevoEstado);
    Turno updatedTurno = turnoRepository.save(turno);
    return turnoMapper.toDto(updatedTurno);
  }

}