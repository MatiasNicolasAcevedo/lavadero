package tech.munidigital.lavadero.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import tech.munidigital.lavadero.entity.enums.EstadoTurno;
import tech.munidigital.lavadero.entity.enums.TipoServicio;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoRequestDTO {

    @NotNull(message = "La fecha y hora es requerida")
    private LocalDateTime fechaHora;

    @NotNull(message = "El estado del turno es requerido")
    private EstadoTurno estado;

    @NotNull(message = "El tipo de servicio es requerido")
    private TipoServicio tipoServicio;

    @NotNull(message = "El id del vehículo es requerido")
    private Long vehiculoId;

    // ---- Getters y Setters ---------------------------------------

    public @NotNull(message = "La fecha y hora es requerida") LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(@NotNull(message = "La fecha y hora es requerida") LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public @NotNull(message = "El estado del turno es requerido") EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(@NotNull(message = "El estado del turno es requerido") EstadoTurno estado) {
        this.estado = estado;
    }

    public @NotNull(message = "El tipo de servicio es requerido") TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(@NotNull(message = "El tipo de servicio es requerido") TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public @NotNull(message = "El id del vehículo es requerido") Long getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(@NotNull(message = "El id del vehículo es requerido") Long vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

}