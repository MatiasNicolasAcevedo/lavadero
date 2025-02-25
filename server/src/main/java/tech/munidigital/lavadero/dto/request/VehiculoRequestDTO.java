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

    // ---- Getters y Setters ---------------------------------------

    public @NotBlank(message = "El modelo es requerido") String getModelo() {
        return modelo;
    }

    public void setModelo(@NotBlank(message = "El modelo es requerido") String modelo) {
        this.modelo = modelo;
    }

    public @NotBlank(message = "La matrícula es requerida") String getMatricula() {
        return matricula;
    }

    public void setMatricula(@NotBlank(message = "La matrícula es requerida") String matricula) {
        this.matricula = matricula;
    }

    public @NotNull(message = "El tipo de vehículo es requerido") TipoVehiculo getTipo() {
        return tipo;
    }

    public void setTipo(@NotNull(message = "El tipo de vehículo es requerido") TipoVehiculo tipo) {
        this.tipo = tipo;
    }

    public Map<String, Object> getPropiedadesAdicionales() {
        return propiedadesAdicionales;
    }

    public void setPropiedadesAdicionales(Map<String, Object> propiedadesAdicionales) {
        this.propiedadesAdicionales = propiedadesAdicionales;
    }

    public @NotNull(message = "El id del cliente es requerido") Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(@NotNull(message = "El id del cliente es requerido") Long clienteId) {
        this.clienteId = clienteId;
    }

}