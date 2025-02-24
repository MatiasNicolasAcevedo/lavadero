package tech.munidigital.lavadero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.munidigital.lavadero.entity.Cobro;
import java.util.Optional;

@Repository
public interface CobroRepository extends JpaRepository<Cobro, Long> {

    Optional<Cobro> findByTurnoId(Long turnoId);

}