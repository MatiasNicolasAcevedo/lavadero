package tech.munidigital.lavadero.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tech.munidigital.lavadero.entity.enums.TipoVehiculo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@ToString(exclude = {"cliente", "turnos"})
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El modelo es requerido")
    private String modelo;

    @NotBlank(message = "La matrícula es requerida")
    @Column(nullable = false)
    private String matricula;

    @NotNull(message = "El tipo de vehículo es requerido")
    @Enumerated(EnumType.STRING)
    private TipoVehiculo tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Turno> turnos = new ArrayList<>();

    // Atributos dinámicos
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> propiedadesAdicionales = new HashMap<>();

    public Vehiculo(String modelo, String matricula, TipoVehiculo tipo) {
        this.modelo = modelo;
        this.matricula = matricula;
        this.tipo = tipo;
    }

    // ---- Getters y Setters ---------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    public Map<String, Object> getPropiedadesAdicionales() {
        return propiedadesAdicionales;
    }

    public void setPropiedadesAdicionales(Map<String, Object> propiedadesAdicionales) {
        this.propiedadesAdicionales = propiedadesAdicionales;
    }

}