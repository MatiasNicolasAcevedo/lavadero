package tech.munidigital.lavadero.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.munidigital.lavadero.dto.request.TurnoRequestDTO;
import tech.munidigital.lavadero.dto.response.TurnoResponseDTO;
import tech.munidigital.lavadero.entity.enums.EstadoTurno;
import tech.munidigital.lavadero.service.TurnoService;
import java.util.List;

@Tag(
    name = "Turnos",
    description = "Operaciones para gestionar turnos asignados a vehículos en el sistema Lavadero"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/turnos")
public class TurnoController {

  private final TurnoService turnoService;

  @Operation(
      summary = "Crear un nuevo turno",
      description = "Registra un nuevo turno en el sistema con los datos del vehículo, cliente y servicios solicitados."
  )
  @PostMapping
  public ResponseEntity<TurnoResponseDTO> createTurno(@Valid @RequestBody TurnoRequestDTO turnoRequestDTO) {
    TurnoResponseDTO response = turnoService.createTurno(turnoRequestDTO);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Operation(
      summary = "Obtener turno por ID",
      description = "Devuelve el detalle de un turno específico según su identificador único."
  )
  @GetMapping("/{id}")
  public ResponseEntity<TurnoResponseDTO> getTurnoById(@PathVariable Long id) {
    TurnoResponseDTO response = turnoService.getTurnoById(id);
    return ResponseEntity.ok(response);
  }

  @Operation(
      summary = "Listar turnos por ID de vehículo",
      description = "Devuelve todos los turnos registrados en el sistema para un vehículo determinado."
  )
  @GetMapping("/vehiculo/{vehiculoId}")
  public ResponseEntity<List<TurnoResponseDTO>> getTurnosByVehiculoId(@PathVariable Long vehiculoId) {
    List<TurnoResponseDTO> response = turnoService.getTurnosByVehiculoId(vehiculoId);
    return ResponseEntity.ok(response);
  }

  @Operation(
      summary = "Actualizar turno a EN_PROCESO",
      description = "Modifica el estado de un turno existente a EN_PROCESO. Este cambio indica que el servicio ha comenzado."
  )
  @PutMapping("/{id}/en-proceso")
  public ResponseEntity<TurnoResponseDTO> setTurnoEnProceso(@PathVariable Long id) {
    TurnoResponseDTO response = turnoService.updateTurnoEstado(id, EstadoTurno.EN_PROCESO);
    return ResponseEntity.ok(response);
  }

  @Operation(
      summary = "Actualizar turno a FINALIZADO",
      description = "Modifica el estado de un turno existente a FINALIZADO. Indica que el servicio ya fue completado."
  )
  @PutMapping("/{id}/finalizado")
  public ResponseEntity<TurnoResponseDTO> setTurnoFinalizado(@PathVariable Long id) {
    TurnoResponseDTO response = turnoService.updateTurnoEstado(id, EstadoTurno.FINALIZADO);
    return ResponseEntity.ok(response);
  }

}