package tech.munidigital.lavadero.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "vehiculos")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "El correo electrónico es requerido")
    @Email(message = "El correo electrónico debe ser válido")
    @Column(unique = true)
    private String correoElectronico;

    @NotBlank(message = "El teléfono es requerido")
    private String telefono;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehiculo> vehiculos = new ArrayList<>();

    public Cliente(String nombre, String correoElectronico, String telefono) {
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
    }

    public void addVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
        vehiculo.setCliente(this);
    }

    public void removeVehiculo(Vehiculo vehiculo) {
        vehiculos.remove(vehiculo);
        vehiculo.setCliente(null);
    }

}