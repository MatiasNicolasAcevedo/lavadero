package tech.munidigital.lavadero.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import tech.munidigital.lavadero.dto.request.CobroRequestDTO;
import tech.munidigital.lavadero.dto.response.CobroResponseDTO;
import tech.munidigital.lavadero.entity.Cobro;
import tech.munidigital.lavadero.entity.Turno;
import tech.munidigital.lavadero.entity.enums.EstadoTurno;
import tech.munidigital.lavadero.mappers.CobroMapper;
import tech.munidigital.lavadero.repository.CobroRepository;
import tech.munidigital.lavadero.repository.TurnoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CobroServiceImplTest {

  private static final BigDecimal MONTO = new BigDecimal("100.00");
  private static final Long TURNO_ID = 1L;

  @Mock
  private CobroRepository cobroRepository;
  @Mock
  private TurnoRepository turnoRepository;
  @Mock
  private CobroMapper cobroMapper;
  @InjectMocks
  private CobroServiceImpl cobroService;

  private CobroRequestDTO requestDTO;
  private Turno turnoFinalizado;
  private Cobro cobro;
  private CobroResponseDTO responseDTO;

  @BeforeEach
  void setUp() {
    requestDTO = CobroRequestDTO.builder()
        .monto(MONTO)
        .fecha(LocalDate.now())
        .turnoId(TURNO_ID)
        .build();

    turnoFinalizado = Turno.builder()
        .id(TURNO_ID)
        .estado(EstadoTurno.FINALIZADO)
        .build();

    cobro = Cobro.builder()
        .id(1L)
        .monto(MONTO)
        .fecha(requestDTO.getFecha())
        .turno(turnoFinalizado)
        .build();

    responseDTO = CobroResponseDTO.builder()
        .id(cobro.getId())
        .monto(cobro.getMonto())
        .fecha(cobro.getFecha())
        .turnoId(TURNO_ID)
        .build();
  }

  /* createCobro */

  @Test
  void createCobro_whenValid_returnsResponse() {
    when(turnoRepository.findById(TURNO_ID)).thenReturn(Optional.of(turnoFinalizado));
    when(cobroMapper.toEntity(requestDTO)).thenReturn(cobro);
    when(cobroRepository.save(cobro)).thenReturn(cobro);
    when(cobroMapper.toDto(cobro)).thenReturn(responseDTO);

    CobroResponseDTO result = cobroService.createCobro(requestDTO);

    assertThat(result)
        .usingRecursiveComparison()
        .isEqualTo(responseDTO);

    verify(turnoRepository).findById(TURNO_ID);
    verify(cobroRepository).save(cobro);
  }

  @Test
  void createCobro_whenTurnoMissing_throws404() {
    when(turnoRepository.findById(TURNO_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> cobroService.createCobro(requestDTO))
        .isInstanceOf(ResponseStatusException.class)
        .hasMessageContaining("Turno no encontrado");

    verify(turnoRepository).findById(TURNO_ID);
    verify(cobroRepository, never()).save(any());
  }

  @Test
  void createCobro_whenTurnoNotFinalizado_throws400() {
    Turno turnoPendiente = Turno.builder()
        .id(TURNO_ID)
        .estado(EstadoTurno.PENDIENTE)
        .build();
    when(turnoRepository.findById(TURNO_ID)).thenReturn(Optional.of(turnoPendiente));

    assertThatThrownBy(() -> cobroService.createCobro(requestDTO))
        .isInstanceOf(ResponseStatusException.class)
        .hasMessageContaining("solo se puede realizar si el turno está finalizado");

    verify(turnoRepository).findById(TURNO_ID);
    verify(cobroRepository, never()).save(any());
  }

  @Test
  void createCobro_whenTurnoHasCobro_throws400() {
    turnoFinalizado.setCobro(Cobro.builder().id(99L).build());
    when(turnoRepository.findById(TURNO_ID)).thenReturn(Optional.of(turnoFinalizado));

    assertThatThrownBy(() -> cobroService.createCobro(requestDTO))
        .isInstanceOf(ResponseStatusException.class)
        .hasMessageContaining("Ya existe un cobro para este turno");

    verify(turnoRepository).findById(TURNO_ID);
    verify(cobroRepository, never()).save(any());
  }

}
