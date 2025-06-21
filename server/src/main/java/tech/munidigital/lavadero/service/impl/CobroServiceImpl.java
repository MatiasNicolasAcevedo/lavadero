package tech.munidigital.lavadero.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.munidigital.lavadero.dto.request.CobroRequestDTO;
import tech.munidigital.lavadero.dto.response.CobroResponseDTO;
import tech.munidigital.lavadero.entity.Cobro;
import tech.munidigital.lavadero.entity.Turno;
import tech.munidigital.lavadero.entity.enums.EstadoTurno;
import tech.munidigital.lavadero.mappers.CobroMapper;
import tech.munidigital.lavadero.repository.CobroRepository;
import tech.munidigital.lavadero.repository.TurnoRepository;
import tech.munidigital.lavadero.service.CobroService;

/**
 * Implementación de {@link CobroService} encargada de registrar
 * cobros asociados a turnos ya finalizados.
 */
@Service
@RequiredArgsConstructor
public class CobroServiceImpl implements CobroService {

  private final CobroRepository cobroRepository;
  private final TurnoRepository turnoRepository;
  private final CobroMapper cobroMapper;

  /**
   * Registra un cobro para un turno finalizado.
   * <ol>
   *   <li>Verifica que el turno exista.</li>
   *   <li>Comprueba que el turno esté en estado {@code FINALIZADO}.</li>
   *   <li>Evita duplicar cobros sobre el mismo turno.</li>
   * </ol>
   *
   * @param cobroRequestDTO datos del cobro (monto, fecha, id del turno).
   * @return DTO con la información del cobro persistido.
   * @throws ResponseStatusException 404 si el turno no existe.
   * @throws ResponseStatusException 400 si el turno no está finalizado
   *                                 o si ya tiene un cobro asociado.
   */
  @Override
  public CobroResponseDTO createCobro(CobroRequestDTO cobroRequestDTO) {

    // 1) Verificar existencia del turno
    Turno turno = turnoRepository.findById(cobroRequestDTO.getTurnoId())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Turno no encontrado con id: " + cobroRequestDTO.getTurnoId()
        ));

    // 2) Validar estado FINALIZADO
    if (turno.getEstado() != EstadoTurno.FINALIZADO) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "El cobro solo se puede realizar si el turno está finalizado"
      );
    }

    // 3) Evitar duplicidad de cobros
    if (turno.getCobro() != null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Ya existe un cobro para este turno"
      );
    }

    // 4) Mapear, enlazar y persistir
    Cobro cobro = cobroMapper.toEntity(cobroRequestDTO);
    cobro.setTurno(turno);
    turno.setCobro(cobro);

    Cobro savedCobro = cobroRepository.save(cobro);
    return cobroMapper.toDto(savedCobro);
  }

}