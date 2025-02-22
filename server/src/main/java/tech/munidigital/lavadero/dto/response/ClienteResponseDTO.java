package tech.munidigital.lavadero.dto.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {

    private Long id;
    private String nombre;
    private String correoElectronico;
    private String telefono;
    private List<VehiculoResponseDTO> vehiculos;

}