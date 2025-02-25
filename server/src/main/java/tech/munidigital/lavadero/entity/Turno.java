package tech.munidigital.lavadero.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tech.munidigital.lavadero.entity.enums.EstadoTurno;
import tech.munidigital.lavadero.entity.enums.TipoServicio;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(exclude = {"vehiculo", "cobro"})
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "turnos")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha y hora es requerida")
    private LocalDateTime fechaHora;

    @NotNull(message = "El estado del turno es requerido")
    @Enumerated(EnumType.STRING)
    private EstadoTurno estado;

    @NotNull(message = "El tipo de servicio es requerido")
    @Enumerated(EnumType.STRING)
    private TipoServicio tipoServicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @OneToOne(mappedBy = "turno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cobro cobro;

    public Turno(LocalDateTime fechaHora, EstadoTurno estado, TipoServicio tipoServicio) {
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.tipoServicio = tipoServicio;
    }

    // ---- Getters y Setters ---------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Cobro getCobro() {
        return cobro;
    }

    public void setCobro(Cobro cobro) {
        this.cobro = cobro;
    }

}