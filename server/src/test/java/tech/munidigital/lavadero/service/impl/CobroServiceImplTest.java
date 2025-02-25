package tech.munidigital.lavadero.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
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

@ExtendWith(MockitoExtension.class)
public class CobroServiceImplTest {

    @Mock
    private CobroRepository cobroRepository;

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private CobroMapper cobroMapper;

    @InjectMocks
    private CobroServiceImpl cobroService;

    private CobroRequestDTO cobroRequestDTO;
    private Turno turno;
    private Cobro cobro;
    private CobroResponseDTO cobroResponseDTO;

    @BeforeEach
    void setUp() {
        // Configuramos un CobroRequestDTO válido
        cobroRequestDTO = new CobroRequestDTO();
        cobroRequestDTO.setMonto(new BigDecimal("100.00"));
        cobroRequestDTO.setFecha(LocalDate.now());
        cobroRequestDTO.setTurnoId(1L);

        // Configuramos un Turno válido, con estado FINALIZADO y sin cobro asignado
        turno = new Turno();
        turno.setId(1L);
        turno.setEstado(EstadoTurno.FINALIZADO);
        turno.setCobro(null);

        // Configuramos una entidad Cobro (sin id inicialmente, se asigna tras guardar)
        cobro = new Cobro();
        cobro.setMonto(cobroRequestDTO.getMonto());
        cobro.setFecha(cobroRequestDTO.getFecha());
        cobro.setTurno(turno);

        // Simulamos que el cobro se guarda y se le asigna un id
        cobro.setId(1L);

        // Configuramos el DTO de respuesta
        cobroResponseDTO = new CobroResponseDTO();
        cobroResponseDTO.setId(1L);
        cobroResponseDTO.setMonto(cobro.getMonto());
        cobroResponseDTO.setFecha(cobro.getFecha());
        cobroResponseDTO.setTurnoId(turno.getId());
    }

    @Test
    void createCobro_validRequest_returnsCobroResponseDTO() {
        // Simulamos que se encuentra el turno
        when(turnoRepository.findById(cobroRequestDTO.getTurnoId()))
                .thenReturn(Optional.of(turno));

        // Simulamos el mapeo de DTO a entidad
        when(cobroMapper.toEntity(cobroRequestDTO)).thenReturn(cobro);

        // Simulamos la operación de guardado
        when(cobroRepository.save(cobro)).thenReturn(cobro);

        // Simulamos el mapeo de la entidad guardada a DTO de respuesta
        when(cobroMapper.toDto(cobro)).thenReturn(cobroResponseDTO);

        // Ejecutamos el servicio
        CobroResponseDTO result = cobroService.createCobro(cobroRequestDTO);

        // Verificamos resultados
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(new BigDecimal("100.00"), result.getMonto());
        assertEquals(turno.getId(), result.getTurnoId());
        verify(turnoRepository).findById(cobroRequestDTO.getTurnoId());
        verify(cobroRepository).save(cobro);
    }

    @Test
    void createCobro_turnoNotFound_throwsException() {
        // Simulamos que no se encuentra el turno
        when(turnoRepository.findById(cobroRequestDTO.getTurnoId())).thenReturn(Optional.empty());

        // Verificamos que se lance la excepción con el mensaje adecuado
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cobroService.createCobro(cobroRequestDTO);
        });
        assertTrue(exception.getReason().contains("Turno no encontrado con id: " + cobroRequestDTO.getTurnoId()));
        verify(turnoRepository).findById(cobroRequestDTO.getTurnoId());
        verify(cobroRepository, never()).save(any(Cobro.class));
    }

    @Test
    void createCobro_turnoNotFinalized_throwsException() {
        // Configuramos el turno con un estado distinto a FINALIZADO
        turno.setEstado(EstadoTurno.PENDIENTE);
        when(turnoRepository.findById(cobroRequestDTO.getTurnoId())).thenReturn(Optional.of(turno));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cobroService.createCobro(cobroRequestDTO);
        });
        assertTrue(exception.getReason().contains("El cobro solo se puede realizar si el turno está finalizado"));
        verify(turnoRepository).findById(cobroRequestDTO.getTurnoId());
        verify(cobroRepository, never()).save(any(Cobro.class));
    }

    @Test
    void createCobro_turnoAlreadyHasCobro_throwsException() {
        // Configuramos el turno con estado FINALIZADO pero ya tiene un cobro asociado
        Cobro existingCobro = new Cobro();
        existingCobro.setId(2L);
        turno.setCobro(existingCobro);

        when(turnoRepository.findById(cobroRequestDTO.getTurnoId())).thenReturn(Optional.of(turno));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cobroService.createCobro(cobroRequestDTO);
        });
        assertTrue(exception.getReason().contains("Ya existe un cobro para este turno"));
        verify(turnoRepository).findById(cobroRequestDTO.getTurnoId());
        verify(cobroRepository, never()).save(any(Cobro.class));
    }

}