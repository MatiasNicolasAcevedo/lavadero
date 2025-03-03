package tech.munidigital.lavadero.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tech.munidigital.lavadero.entity.enums.TipoVehiculo;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoRequestDTO {

    @NotBlank(message = "El modelo es requerido")
    private String modelo;

    @NotBlank(message = "La matrícula es requerida")
    private String matricula;

    @NotNull(message = "El tipo de vehículo es requerido")
    private TipoVehiculo tipo;

    private Map<String, Object> propiedadesAdicionales;

    @NotNull(message = "El id del cliente es requerido")
    private Long clienteId;

}