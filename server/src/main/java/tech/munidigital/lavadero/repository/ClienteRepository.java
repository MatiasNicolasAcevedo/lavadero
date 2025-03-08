package tech.munidigital.lavadero.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.munidigital.lavadero.entity.Cliente;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCorreoElectronico(String correoElectronico);

    // Método para búsqueda paginada que trae los clientes cuyo nombre inicia con la letra indicada
    Page<Cliente> findByNombreStartingWithIgnoreCase(String nombre, Pageable pageable);

}