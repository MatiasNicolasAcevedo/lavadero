package tech.munidigital.lavadero.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {

  private Long id;
  private String nombre;
  private String correoElectronico;
  private String telefono;
  private List<VehiculoResponseDTO> vehiculos;

}