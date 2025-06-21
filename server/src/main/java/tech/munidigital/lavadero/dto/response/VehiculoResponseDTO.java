package tech.munidigital.lavadero.dto.response;

import lombok.*;
import tech.munidigital.lavadero.entity.enums.TipoVehiculo;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoResponseDTO {

  private Long id;
  private String modelo;
  private String matricula;
  private TipoVehiculo tipo;
  private Long clienteId;

  // Propiedades dinámicas (siempre se devolverá un mapa, aunque esté vacío).
  private Map<String, Object> propiedadesAdicionales;

}