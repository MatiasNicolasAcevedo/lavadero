package tech.munidigital.lavadero.dto.response;

import lombok.*;
import tech.munidigital.lavadero.entity.enums.EstadoTurno;
import tech.munidigital.lavadero.entity.enums.TipoServicio;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TurnoResponseDTO {

    private Long id;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;
    private TipoServicio tipoServicio;

    // Opcional: incluir el id del vehículo sin agregar el objeto completo
    private Long vehiculoId;

    // Si se requiere mostrar el cobro asociado, incluir el DTO de cobro (sin incluir nuevamente el turno)
    // private CobroResponseDTO cobro;

}