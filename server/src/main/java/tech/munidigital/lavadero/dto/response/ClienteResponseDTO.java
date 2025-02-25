package tech.munidigital.lavadero.dto.response;

import lombok.*;
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

    // ---- Getters y Setters ---------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<VehiculoResponseDTO> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<VehiculoResponseDTO> vehiculos) {
        this.vehiculos = vehiculos;
    }

}