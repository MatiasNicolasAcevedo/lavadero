package tech.munidigital.lavadero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.munidigital.lavadero.entity.Turno;
import java.util.List;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    List<Turno> findByVehiculoId(Long vehiculoId);

}