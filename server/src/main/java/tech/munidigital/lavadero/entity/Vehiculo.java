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

@Builder
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

}