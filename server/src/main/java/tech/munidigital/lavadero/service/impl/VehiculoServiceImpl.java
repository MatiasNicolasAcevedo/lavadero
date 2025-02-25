package tech.munidigital.lavadero.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.munidigital.lavadero.dto.request.VehiculoRequestDTO;
import tech.munidigital.lavadero.dto.response.VehiculoResponseDTO;
import tech.munidigital.lavadero.entity.Vehiculo;
import tech.munidigital.lavadero.mappers.VehiculoMapper;
import tech.munidigital.lavadero.repository.ClienteRepository;
import tech.munidigital.lavadero.repository.VehiculoRepository;
import tech.munidigital.lavadero.service.VehiculoService;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final ClienteRepository clienteRepository;
    private final VehiculoMapper vehiculoMapper;

    @Override
    public VehiculoResponseDTO createVehiculo(VehiculoRequestDTO vehiculoRequestDTO) {
        // Validar que el cliente exista
        var cliente = clienteRepository.findById(vehiculoRequestDTO.getClienteId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente no encontrado con id: " + vehiculoRequestDTO.getClienteId()
                ));

        // Mapear el DTO a la entidad
        Vehiculo vehiculo = vehiculoMapper.toEntity(vehiculoRequestDTO);

        // Asociar el cliente al vehículo
        vehiculo.setCliente(cliente);

        // Guardar el vehículo
        Vehiculo savedVehiculo = vehiculoRepository.save(vehiculo);

        // Mapear la entidad guardada a DTO de respuesta
        return vehiculoMapper.toDto(savedVehiculo);
    }

    @Override
    public VehiculoResponseDTO getVehiculoById(Long id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Vehículo no encontrado con id: " + id
                ));
        return vehiculoMapper.toDto(vehiculo);
    }

    @Override
    public List<VehiculoResponseDTO> getVehiculosByClienteId(Long clienteId) {
        List<Vehiculo> vehiculos = vehiculoRepository.findByClienteId(clienteId);
        return vehiculos.stream()
                .map(vehiculoMapper::toDto)
                .collect(Collectors.toList());
    }

}