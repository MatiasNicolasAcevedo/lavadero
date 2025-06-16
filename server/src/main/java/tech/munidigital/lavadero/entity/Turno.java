package tech.munidigital.lavadero.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tech.munidigital.lavadero.entity.enums.EstadoTurno;
import tech.munidigital.lavadero.entity.enums.TipoServicio;
import java.time.LocalDateTime;

@Builder
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

}