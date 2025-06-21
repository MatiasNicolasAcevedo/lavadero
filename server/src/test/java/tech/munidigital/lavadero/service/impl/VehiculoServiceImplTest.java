package tech.munidigital.lavadero.service.impl;

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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculoServiceImplTest {

  private static final long CLIENTE_ID = 1L;
  private static final long VEHICULO_ID = 10L;
  private static final String MATRICULA = "ABC123";

  @Mock
  private VehiculoRepository vehiculoRepository;
  @Mock
  private ClienteRepository clienteRepository;
  @Mock
  private VehiculoMapper vehiculoMapper;

  @InjectMocks
  private VehiculoServiceImpl vehiculoService;

  private VehiculoRequestDTO requestDTO;
  private Cliente cliente;
  private Vehiculo entity;
  private VehiculoResponseDTO responseDTO;

  @BeforeEach
  void setUp() {

    requestDTO = VehiculoRequestDTO.builder()
        .modelo("Modelo X")
        .matricula(MATRICULA)
        .tipo(TipoVehiculo.SEDAN)
        .clienteId(CLIENTE_ID)
        .propiedadesAdicionales(Map.of("color", "Rojo"))
        .build();

    cliente = Cliente.builder()
        .id(CLIENTE_ID)
        .nombre("Cliente Test")
        .correoElectronico("cliente@test.com")
        .telefono("1234567890")
        .build();

    entity = Vehiculo.builder()
        .id(VEHICULO_ID)
        .modelo(requestDTO.getModelo())
        .matricula(MATRICULA)
        .tipo(TipoVehiculo.SEDAN)
        .cliente(cliente)
        .propiedadesAdicionales(requestDTO.getPropiedadesAdicionales())
        .build();

    responseDTO = VehiculoResponseDTO.builder()
        .id(VEHICULO_ID)
        .modelo(entity.getModelo())
        .matricula(entity.getMatricula())
        .tipo(entity.getTipo())
        .clienteId(CLIENTE_ID)
        .propiedadesAdicionales(entity.getPropiedadesAdicionales())
        .build();
  }

  /* createVehiculo */

  @Test
  void createVehiculo_whenValid_returnsResponse() {
    when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.of(cliente));
    when(vehiculoMapper.toEntity(requestDTO)).thenReturn(entity);
    when(vehiculoRepository.save(entity)).thenReturn(entity);
    when(vehiculoMapper.toDto(entity)).thenReturn(responseDTO);

    VehiculoResponseDTO result = vehiculoService.createVehiculo(requestDTO);

    assertThat(result)
        .usingRecursiveComparison()
        .isEqualTo(responseDTO);

    verify(clienteRepository).findById(CLIENTE_ID);
    verify(vehiculoRepository).save(entity);
  }

  @Test
  void createVehiculo_whenClientMissing_throws404() {
    when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> vehiculoService.createVehiculo(requestDTO))
        .isInstanceOf(ResponseStatusException.class)
        .hasMessageContaining("Cliente no encontrado");

    verify(clienteRepository).findById(CLIENTE_ID);
    verify(vehiculoRepository, never()).save(any());
  }

  /* getVehiculoById */

  @Test
  void getVehiculoById_whenExists_returnsResponse() {
    when(vehiculoRepository.findById(VEHICULO_ID)).thenReturn(Optional.of(entity));
    when(vehiculoMapper.toDto(entity)).thenReturn(responseDTO);

    VehiculoResponseDTO result = vehiculoService.getVehiculoById(VEHICULO_ID);

    assertThat(result.getId()).isEqualTo(VEHICULO_ID);
    verify(vehiculoRepository).findById(VEHICULO_ID);
  }

  @Test
  void getVehiculoById_whenMissing_throws404() {
    when(vehiculoRepository.findById(VEHICULO_ID)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> vehiculoService.getVehiculoById(VEHICULO_ID))
        .isInstanceOf(ResponseStatusException.class)
        .hasMessageContaining("Vehículo no encontrado");

    verify(vehiculoRepository).findById(VEHICULO_ID);
  }

  /* getVehiculosByClienteId */

  @Test
  void getVehiculosByClienteId_returnsList() {
    when(vehiculoRepository.findByClienteId(CLIENTE_ID)).thenReturn(List.of(entity));
    when(vehiculoMapper.toDto(entity)).thenReturn(responseDTO);

    List<VehiculoResponseDTO> result = vehiculoService.getVehiculosByClienteId(CLIENTE_ID);

    assertThat(result)
        .hasSize(1)
        .first()
        .isEqualTo(responseDTO);

    verify(vehiculoRepository).findByClienteId(CLIENTE_ID);
  }

}
