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

    // En vez de incluir el turno completo, incluir solo el id del turno
    private Long turnoId;

}
