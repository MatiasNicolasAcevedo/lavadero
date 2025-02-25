package tech.munidigital.lavadero.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    public Cobro(BigDecimal monto, LocalDate fecha) {
        this.monto = monto;
        this.fecha = fecha;
    }

    // ---- Getters y Setters ---------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

}