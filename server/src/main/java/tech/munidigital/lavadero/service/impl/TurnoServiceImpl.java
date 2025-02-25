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

@Service
@RequiredArgsConstructor
public class TurnoServiceImpl implements TurnoService {

    private final TurnoRepository turnoRepository;
    private final VehiculoRepository vehiculoRepository;
    private final TurnoMapper turnoMapper;

    @Override
    public TurnoResponseDTO createTurno(TurnoRequestDTO turnoRequestDTO) {
        // Validar que la fecha no sea en el pasado
        if (turnoRequestDTO.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha y hora no pueden ser en el pasado");
        }

        // Validar que el vehículo exista
        Vehiculo vehiculo = vehiculoRepository.findById(turnoRequestDTO.getVehiculoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Vehículo no encontrado con id: " + turnoRequestDTO.getVehiculoId()
                ));

        // Mapear el DTO a la entidad
        Turno turno = turnoMapper.toEntity(turnoRequestDTO);
        turno.setVehiculo(vehiculo);

        // Guardar el turno
        Turno savedTurno = turnoRepository.save(turno);

        // Mapear la entidad guardada a DTO de respuesta
        return turnoMapper.toDto(savedTurno);
    }

    @Override
    public TurnoResponseDTO getTurnoById(Long id) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Turno no encontrado con id: " + id
                ));
        return turnoMapper.toDto(turno);
    }

    @Override
    public List<TurnoResponseDTO> getTurnosByVehiculoId(Long vehiculoId) {
        List<Turno> turnos = turnoRepository.findByVehiculoId(vehiculoId);
        return turnos.stream()
                .map(turnoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TurnoResponseDTO updateTurnoEstado(Long id, EstadoTurno nuevoEstado) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Turno no encontrado con id: " + id
                ));

        turno.setEstado(nuevoEstado);
        Turno updatedTurno = turnoRepository.save(turno);
        return turnoMapper.toDto(updatedTurno);
    }

}