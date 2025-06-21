package tech.munidigital.lavadero.dto.response;

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
public class CobroResponseDTO {

  private Long id;
  private BigDecimal monto;
  private LocalDate fecha;
  private Long turnoId;

}