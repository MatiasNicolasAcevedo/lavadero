package tech.munidigital.lavadero.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequestDTO {

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "El correo electrónico es requerido")
    @Email(message = "El correo electrónico debe ser válido")
    private String correoElectronico;

    @NotBlank(message = "El teléfono es requerido")
    private String telefono;

    // ---- Getters y Setters ---------------------------------------

    public @NotBlank(message = "El nombre es requerido") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "El nombre es requerido") String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank(message = "El correo electrónico es requerido") @Email(message = "El correo electrónico debe ser válido") String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(@NotBlank(message = "El correo electrónico es requerido") @Email(message = "El correo electrónico debe ser válido") String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public @NotBlank(message = "El teléfono es requerido") String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NotBlank(message = "El teléfono es requerido") String telefono) {
        this.telefono = telefono;
    }

}