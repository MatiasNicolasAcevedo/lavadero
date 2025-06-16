package tech.munidigital.lavadero.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.Optional;
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

@ExtendWith(MockitoExtension.class)
public class TurnoServiceImplTest {

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private TurnoMapper turnoMapper;

    @InjectMocks
    private TurnoServiceImpl turnoService;

    private TurnoRequestDTO turnoRequestDTO;
    private Vehiculo vehiculo;
    private Turno turno;

    @BeforeEach
    void setUp() {
        // Configuramos un TurnoRequestDTO con una fecha en el futuro
        turnoRequestDTO = new TurnoRequestDTO();
        turnoRequestDTO.setFechaHora(LocalDateTime.now().plusDays(1));
        turnoRequestDTO.setEstado(EstadoTurno.PENDIENTE);
        turnoRequestDTO.setTipoServicio(TipoServicio.LAVADO_COMPLETO);
        turnoRequestDTO.setVehiculoId(1L);

        // Configuramos el vehículo simulado
        vehiculo = new Vehiculo();
        vehiculo.setId(1L);

        // Configuramos el turno simulado
        turno = new Turno();
        turno.setId(1L);
        turno.setFechaHora(turnoRequestDTO.getFechaHora());
        turno.setEstado(turnoRequestDTO.getEstado());
        turno.setTipoServicio(turnoRequestDTO.getTipoServicio());
    }

    @Test
    void createTurno_ValidRequest_ReturnsTurnoResponseDTO() {
        // Simular que se encuentra el vehículo
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));

        // Simular el mapeo de DTO a entidad
        when(turnoMapper.toEntity(turnoRequestDTO)).thenReturn(turno);

        // Simular la operación de guardado
        when(turnoRepository.save(turno)).thenReturn(turno);

        // Simular el mapeo de la entidad guardada a DTO
        TurnoResponseDTO turnoResponseDTO = new TurnoResponseDTO();
        turnoResponseDTO.setId(turno.getId());
        when(turnoMapper.toDto(turno)).thenReturn(turnoResponseDTO);

        // Ejecutar el servicio
        TurnoResponseDTO result = turnoService.createTurno(turnoRequestDTO);

        // Verificar resultados
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(vehiculoRepository).findById(1L);
        verify(turnoRepository).save(turno);
    }

    @Test
    void createTurno_FechaEnElPasado_ThrowsException() {
        // Configurar la fecha en el pasado
        turnoRequestDTO.setFechaHora(LocalDateTime.now().minusDays(1));

        // Se espera que se lance una excepción de validación
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            turnoService.createTurno(turnoRequestDTO);
        });
        assertTrue(exception.getReason().contains("La fecha y hora no pueden ser en el pasado"));
    }

}