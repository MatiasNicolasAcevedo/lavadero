package tech.munidigital.lavadero.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tech.munidigital.lavadero.entity.enums.TipoVehiculo;
import tech.munidigital.lavadero.util.JsonAttributeConverter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@ToString(exclude = {"cliente", "turnos"})
@Builder
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
    @Convert(converter = JsonAttributeConverter.class)
    @Column(columnDefinition = "json")
    private Map<String, Object> propiedadesAdicionales = new HashMap<>();

    public Vehiculo(String modelo, String matricula, TipoVehiculo tipo) {
        this.modelo = modelo;
        this.matricula = matricula;
        this.tipo = tipo;
    }

}