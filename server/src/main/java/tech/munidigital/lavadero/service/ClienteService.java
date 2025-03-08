package tech.munidigital.lavadero.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.munidigital.lavadero.dto.request.ClienteRequestDTO;
import tech.munidigital.lavadero.dto.response.ClienteResponseDTO;
import java.util.List;

public interface ClienteService {

    ClienteResponseDTO createCliente(ClienteRequestDTO clienteRequestDTO);
    ClienteResponseDTO getClienteById(Long id);
    List<ClienteResponseDTO> getAllClientes();
    Page<ClienteResponseDTO> getClientesPaginados(Pageable pageable, String search);

}