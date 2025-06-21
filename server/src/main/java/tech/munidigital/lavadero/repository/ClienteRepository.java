package tech.munidigital.lavadero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.munidigital.lavadero.entity.Cliente;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  Optional<Cliente> findByCorreoElectronico(String correoElectronico);

}