package tech.munidigital.lavadero.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import tech.munidigital.lavadero.dto.request.ClienteRequestDTO;
import tech.munidigital.lavadero.dto.response.ClienteResponseDTO;
import tech.munidigital.lavadero.entity.Cliente;
import tech.munidigital.lavadero.mappers.ClienteMapper;
import tech.munidigital.lavadero.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

  private static final String EMAIL = "juan.perez@example.com";

  @Mock
  private ClienteRepository clienteRepository;
  @Mock
  private ClienteMapper clienteMapper;

  @InjectMocks
  private ClienteServiceImpl clienteService;

  private ClienteRequestDTO requestDTO;
  private Cliente entity;
  private ClienteResponseDTO responseDTO;

  @BeforeEach
  void setUp() {
    requestDTO = ClienteRequestDTO.builder()
        .nombre("Juan Pérez")
        .correoElectronico(EMAIL)
        .telefono("123456789")
        .build();

    entity = Cliente.builder()
        .id(1L)
        .nombre(requestDTO.getNombre())
        .correoElectronico(EMAIL)
        .telefono(requestDTO.getTelefono())
        .build();

    responseDTO = ClienteResponseDTO.builder()
        .id(entity.getId())
        .nombre(entity.getNombre())
        .correoElectronico(entity.getCorreoElectronico())
        .telefono(entity.getTelefono())
        .build();
  }

  /* createCliente */

  @Test
  void createCliente_whenValid_returnsResponse() {
    when(clienteRepository.findByCorreoElectronico(EMAIL)).thenReturn(Optional.empty());
    when(clienteMapper.toEntity(requestDTO)).thenReturn(entity);
    when(clienteRepository.save(entity)).thenReturn(entity);
    when(clienteMapper.toDto(entity)).thenReturn(responseDTO);

    ClienteResponseDTO result = clienteService.createCliente(requestDTO);

    assertThat(result)
        .usingRecursiveComparison()
        .isEqualTo(responseDTO);

    verify(clienteRepository).findByCorreoElectronico(EMAIL);
    verify(clienteRepository).save(entity);
  }

  @Test
  void createCliente_whenEmailExists_throws400() {
    when(clienteRepository.findByCorreoElectronico(EMAIL)).thenReturn(Optional.of(entity));

    assertThatThrownBy(() -> clienteService.createCliente(requestDTO))
        .isInstanceOf(ResponseStatusException.class)
        .hasMessageContaining("ya está registrado");

    verify(clienteRepository).findByCorreoElectronico(EMAIL);
    verify(clienteRepository, never()).save(any());
  }

  /* getClienteById */

  @Test
  void getClienteById_whenExists_returnsResponse() {
    when(clienteRepository.findById(1L)).thenReturn(Optional.of(entity));
    when(clienteMapper.toDto(entity)).thenReturn(responseDTO);

    ClienteResponseDTO result = clienteService.getClienteById(1L);

    assertThat(result.getId()).isEqualTo(1L);
    verify(clienteRepository).findById(1L);
  }

  @Test
  void getClienteById_whenMissing_throws404() {
    when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> clienteService.getClienteById(1L))
        .isInstanceOf(ResponseStatusException.class)
        .hasMessageContaining("Cliente no encontrado");

    verify(clienteRepository).findById(1L);
  }

  /* getAllClientes */

  @Test
  void getAllClientes_returnsList() {
    when(clienteRepository.findAll()).thenReturn(List.of(entity));
    when(clienteMapper.toDto(entity)).thenReturn(responseDTO);

    List<ClienteResponseDTO> result = clienteService.getAllClientes();

    assertThat(result)
        .hasSize(1)
        .first()
        .isEqualTo(responseDTO);

    verify(clienteRepository).findAll();
  }

}
