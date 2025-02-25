package tech.munidigital.lavadero.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import tech.munidigital.lavadero.dto.request.VehiculoRequestDTO;
import tech.munidigital.lavadero.dto.response.VehiculoResponseDTO;
import tech.munidigital.lavadero.entity.Cliente;
import tech.munidigital.lavadero.entity.Vehiculo;
import tech.munidigital.lavadero.entity.enums.TipoVehiculo;
import tech.munidigital.lavadero.mappers.VehiculoMapper;
import tech.munidigital.lavadero.repository.ClienteRepository;
import tech.munidigital.lavadero.repository.VehiculoRepository;

@ExtendWith(MockitoExtension.class)
public class VehiculoServiceImplTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private VehiculoMapper vehiculoMapper;

    @InjectMocks
    private VehiculoServiceImpl vehiculoService;

    private VehiculoRequestDTO vehiculoRequestDTO;
    private Vehiculo vehiculo;
    private VehiculoResponseDTO vehiculoResponseDTO;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        // Configuramos un VehiculoRequestDTO válido
        vehiculoRequestDTO = new VehiculoRequestDTO();
        vehiculoRequestDTO.setModelo("Modelo X");
        vehiculoRequestDTO.setMatricula("ABC123");
        vehiculoRequestDTO.setTipo(TipoVehiculo.SEDAN);
        vehiculoRequestDTO.setClienteId(1L);
        vehiculoRequestDTO.setPropiedadesAdicionales(Map.of("color", "Rojo"));

        // Configuramos un Cliente simulado
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Cliente Test");
        cliente.setCorreoElectronico("cliente@test.com");
        cliente.setTelefono("1234567890");

        // Configuramos la entidad Vehiculo
        vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setModelo(vehiculoRequestDTO.getModelo());
        vehiculo.setMatricula(vehiculoRequestDTO.getMatricula());
        vehiculo.setTipo(vehiculoRequestDTO.getTipo());
        vehiculo.setCliente(cliente);
        vehiculo.setPropiedadesAdicionales(vehiculoRequestDTO.getPropiedadesAdicionales());

        // Configuramos el DTO de respuesta
        vehiculoResponseDTO = new VehiculoResponseDTO();
        vehiculoResponseDTO.setId(1L);
        vehiculoResponseDTO.setModelo(vehiculo.getModelo());
        vehiculoResponseDTO.setMatricula(vehiculo.getMatricula());
        vehiculoResponseDTO.setTipo(vehiculo.getTipo());
        vehiculoResponseDTO.setClienteId(cliente.getId());
        vehiculoResponseDTO.setPropiedadesAdicionales(vehiculo.getPropiedadesAdicionales());
    }

    @Test
    void createVehiculo_validRequest_returnsVehiculoResponseDTO() {
        // Simulamos que el cliente existe
        when(clienteRepository.findById(vehiculoRequestDTO.getClienteId()))
                .thenReturn(Optional.of(cliente));

        // Simulamos el mapeo de DTO a entidad y la operación de guardado
        when(vehiculoMapper.toEntity(vehiculoRequestDTO)).thenReturn(vehiculo);
        when(vehiculoRepository.save(vehiculo)).thenReturn(vehiculo);

        // Simulamos el mapeo de la entidad guardada a DTO
        when(vehiculoMapper.toDto(vehiculo)).thenReturn(vehiculoResponseDTO);

        VehiculoResponseDTO result = vehiculoService.createVehiculo(vehiculoRequestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Modelo X", result.getModelo());
        verify(clienteRepository).findById(vehiculoRequestDTO.getClienteId());
        verify(vehiculoRepository).save(vehiculo);
    }

    @Test
    void createVehiculo_clientNotFound_throwsException() {
        // Simulamos que no se encuentra el cliente
        when(clienteRepository.findById(vehiculoRequestDTO.getClienteId()))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            vehiculoService.createVehiculo(vehiculoRequestDTO);
        });
        assertTrue(exception.getReason().contains("Cliente no encontrado con id: " + vehiculoRequestDTO.getClienteId()));
        verify(clienteRepository).findById(vehiculoRequestDTO.getClienteId());
        verify(vehiculoRepository, never()).save(any(Vehiculo.class));
    }

    @Test
    void getVehiculoById_existingVehicle_returnsVehiculoResponseDTO() {
        // Simulamos que se encuentra el vehículo
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
        when(vehiculoMapper.toDto(vehiculo)).thenReturn(vehiculoResponseDTO);

        VehiculoResponseDTO result = vehiculoService.getVehiculoById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(vehiculoRepository).findById(1L);
    }

    @Test
    void getVehiculoById_vehicleNotFound_throwsException() {
        // Simulamos que no se encuentra el vehículo
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            vehiculoService.getVehiculoById(1L);
        });
        assertTrue(exception.getReason().contains("Vehículo no encontrado con id: 1"));
        verify(vehiculoRepository).findById(1L);
    }

    @Test
    void getVehiculosByClienteId_returnsListOfVehiculoResponseDTO() {
        // Simulamos que el repositorio retorna una lista con un vehículo
        when(vehiculoRepository.findByClienteId(cliente.getId())).thenReturn(List.of(vehiculo));
        when(vehiculoMapper.toDto(vehiculo)).thenReturn(vehiculoResponseDTO);

        List<VehiculoResponseDTO> result = vehiculoService.getVehiculosByClienteId(cliente.getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(vehiculoRepository).findByClienteId(cliente.getId());
    }

}