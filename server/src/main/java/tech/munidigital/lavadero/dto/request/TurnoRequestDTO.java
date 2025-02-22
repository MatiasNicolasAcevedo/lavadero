package tech.munidigital.lavadero.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import tech.munidigital.lavadero.entity.enums.EstadoTurno;
import tech.munidigital.lavadero.entity.enums.TipoServicio;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TurnoRequestDTO {

    @NotNull(message = "La fecha y hora es requerida")
    private LocalDateTime fechaHora;

    @NotNull(message = "El estado del turno es requerido")
    private EstadoTurno estado;

    @NotNull(message = "El tipo de servicio es requerido")
    private TipoServicio tipoServicio;

    // El id del vehículo asociado
    @NotNull(message = "El id del vehículo es requerido")
    private Long vehiculoId;

}