package tech.munidigital.lavadero.dto.response;

import lombok.*;
import tech.munidigital.lavadero.entity.enums.TipoVehiculo;
import java.util.Map;

@Getter
@Setter
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

    // ---- Getters y Setters ---------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public TipoVehiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoVehiculo tipo) {
        this.tipo = tipo;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Map<String, Object> getPropiedadesAdicionales() {
        return propiedadesAdicionales;
    }

    public void setPropiedadesAdicionales(Map<String, Object> propiedadesAdicionales) {
        this.propiedadesAdicionales = propiedadesAdicionales;
    }

}