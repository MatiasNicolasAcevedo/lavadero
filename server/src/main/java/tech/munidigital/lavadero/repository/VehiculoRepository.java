package tech.munidigital.lavadero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.munidigital.lavadero.entity.Vehiculo;
import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

  List<Vehiculo> findByClienteId(Long clienteId);

}