package tech.munidigital.lavadero.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import tech.munidigital.lavadero.dto.request.TurnoRequestDTO;
import tech.munidigital.lavadero.dto.response.TurnoResponseDTO;
import tech.munidigital.lavadero.entity.Turno;
import tech.munidigital.lavadero.entity.Vehiculo;
import tech.munidigital.lavadero.entity.enums.EstadoTurno;
import tech.munidigital.lavadero.entity.enums.TipoServicio;
import tech.munidigital.lavadero.mappers.TurnoMapper;
import tech.munidigital.lavadero.repository.TurnoRepository;
import tech.munidigital.lavadero.repository.VehiculoRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TurnoServiceImplTest {

  private static final long VEHICULO_ID = 1L;
  private static final long TURNO_ID = 10L;
  private static final LocalDateTime FUTURE_DATE = LocalDateTime.now().plusDays(1);

  @Mock
  private TurnoRepository turnoRepository;
  @Mock
  private VehiculoRepository vehiculoRepository;
  @Mock
  private TurnoMapper turnoMapper;

  @InjectMocks
  private TurnoServiceImpl turnoService;

  private TurnoRequestDTO requestDTO;
  private Vehiculo vehiculo;
  private Turno turno;
  private TurnoResponseDTO responseDTO;

  @BeforeEach
  void setUp() {
    requestDTO = TurnoRequestDTO.builder()
        .fechaHora(FUTURE_DATE)
        .estado(EstadoTurno.PENDIENTE)
        .tipoServicio(TipoServicio.LAVADO_COMPLETO)
        .vehiculoId(VEHICULO_ID)
        .build();

    vehiculo = Vehiculo.builder()
        .id(VEHICULO_ID)
        .build();

    turno = Turno.builder()
        .id(TURNO_ID)
        .fechaHora(FUTURE_DATE)
        .estado(requestDTO.getEstado())
        .tipoServicio(requestDTO.getTipoServicio())
        .build();

    responseDTO = TurnoResponseDTO.builder()
        .id(TURNO_ID)
        .build();
  }

  /* createTurno */

  @Test
  void createTurno_whenValid_returnsResponse() {
    when(vehiculoRepository.findById(VEHICULO_ID)).thenReturn(Optional.of(vehiculo));
    when(turnoMapper.toEntity(requestDTO)).thenReturn(turno);
    when(turnoRepository.save(turno)).thenReturn(turno);
    when(turnoMapper.toDto(turno)).thenReturn(responseDTO);

    TurnoResponseDTO result = turnoService.createTurno(requestDTO);

    assertThat(result.getId()).isEqualTo(TURNO_ID);
    verify(vehiculoRepository).findById(VEHICULO_ID);
    verify(turnoRepository).save(turno);
  }

  @Test
  void createTurno_whenDateInPast_throws400() {
    TurnoRequestDTO pastRequest = TurnoRequestDTO.builder()
        .fechaHora(LocalDateTime.now().minusDays(1))
        .build();

    assertThatThrownBy(() -> turnoService.createTurno(pastRequest))
        .isInstanceOf(ResponseStatusException.class)
        .hasMessageContaining("La fecha y hora no pueden ser en el pasado");
  }

}
