package tech.munidigital.lavadero.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CobroResponseDTO {

    private Long id;
    private BigDecimal monto;
    private LocalDate fecha;
    private Long turnoId;

}
