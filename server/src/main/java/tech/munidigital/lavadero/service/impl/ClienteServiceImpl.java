package tech.munidigital.lavadero.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.munidigital.lavadero.dto.request.ClienteRequestDTO;
import tech.munidigital.lavadero.dto.response.ClienteResponseDTO;
import tech.munidigital.lavadero.entity.Cliente;
import tech.munidigital.lavadero.mappers.ClienteMapper;
import tech.munidigital.lavadero.repository.ClienteRepository;
import tech.munidigital.lavadero.service.ClienteService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public ClienteResponseDTO createCliente(ClienteRequestDTO clienteRequestDTO) {
        // Validar si ya existe un cliente con el mismo correo electrónico
        clienteRepository.findByCorreoElectronico(clienteRequestDTO.getCorreoElectronico())
                .ifPresent(c -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo electrónico ya está registrado.");
                });

        // Convertir el DTO a entidad
        Cliente cliente = clienteMapper.toEntity(clienteRequestDTO);

        // Guardar la entidad
        Cliente savedCliente = clienteRepository.save(cliente);

        // Convertir la entidad guardada a DTO de respuesta
        return clienteMapper.toDto(savedCliente);
    }

    @Override
    public ClienteResponseDTO getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado con id: " + id));
        return clienteMapper.toDto(cliente);
    }

    @Override
    public List<ClienteResponseDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(clienteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ClienteResponseDTO> getClientesPaginados(Pageable pageable, String search) {
        Page<Cliente> clientes;
        if (search != null && !search.trim().isEmpty()) {
            // Se busca solo por los clientes cuyo nombre empieza con la letra enviada
            clientes = clienteRepository.findByNombreStartingWithIgnoreCase(search, pageable);
        } else {
            // Se retorna todos los clientes con paginación y orden
            clientes = clienteRepository.findAll(pageable);
        }
        return clientes.map(clienteMapper::toDto);
    }

}