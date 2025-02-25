package tech.munidigital.lavadero.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
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

    // ---- Getters y Setters ---------------------------------------

    public @NotNull(message = "El monto es requerido") @Positive(message = "El monto debe ser positivo") BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(@NotNull(message = "El monto es requerido") @Positive(message = "El monto debe ser positivo") BigDecimal monto) {
        this.monto = monto;
    }

    public @NotNull(message = "La fecha es requerida") LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(@NotNull(message = "La fecha es requerida") LocalDate fecha) {
        this.fecha = fecha;
    }

    public @NotNull(message = "El id del turno es requerido") Long getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(@NotNull(message = "El id del turno es requerido") Long turnoId) {
        this.turnoId = turnoId;
    }

}