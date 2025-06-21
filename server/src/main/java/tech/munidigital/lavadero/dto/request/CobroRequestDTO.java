package tech.munidigital.lavadero.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CobroRequestDTO {

  @NotNull(message = "El monto es requerido")
  @Positive(message = "El monto debe ser positivo")
  private BigDecimal monto;

  @NotNull(message = "La fecha es requerida")
  private LocalDate fecha;

  @NotNull(message = "El id del turno es requerido")
  private Long turnoId;

}