package tech.munidigital.lavadero.service.impl;

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

@Service
public class CobroServiceImpl implements CobroService {

    private final CobroRepository cobroRepository;
    private final TurnoRepository turnoRepository;
    private final CobroMapper cobroMapper;

    public CobroServiceImpl(CobroRepository cobroRepository, TurnoRepository turnoRepository, CobroMapper cobroMapper) {
        this.cobroRepository = cobroRepository;
        this.turnoRepository = turnoRepository;
        this.cobroMapper = cobroMapper;
    }

    @Override
    public CobroResponseDTO createCobro(CobroRequestDTO cobroRequestDTO) {
        // Validar que el turno exista
        Turno turno = turnoRepository.findById(cobroRequestDTO.getTurnoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Turno no encontrado con id: " + cobroRequestDTO.getTurnoId()
                ));

        // Validar que el turno esté finalizado
        if (turno.getEstado() != EstadoTurno.FINALIZADO) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El cobro solo se puede realizar si el turno está finalizado"
            );
        }

        // Verificar que no exista ya un cobro para este turno
        if (turno.getCobro() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe un cobro para este turno"
            );
        }

        // Mapear el DTO a la entidad
        Cobro cobro = cobroMapper.toEntity(cobroRequestDTO);
        cobro.setTurno(turno);
        turno.setCobro(cobro); // Establecer la relación bidireccional

        // Guardar el cobro
        Cobro savedCobro = cobroRepository.save(cobro);
        return cobroMapper.toDto(savedCobro);
    }

}