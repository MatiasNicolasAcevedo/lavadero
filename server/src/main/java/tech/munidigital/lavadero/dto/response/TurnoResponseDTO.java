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
  private Long vehiculoId;
  private CobroResponseDTO cobro;

}