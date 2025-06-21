package tech.munidigital.lavadero.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Entity
@Getter
@Setter
@ToString(exclude = "turno")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cobros")
public class Cobro {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "El monto es requerido")
  @Positive(message = "El monto debe ser positivo")
  private BigDecimal monto;

  @NotNull(message = "La fecha es requerida")
  private LocalDate fecha;

  @OneToOne
  @JoinColumn(name = "turno_id", unique = true)
  private Turno turno;

}