package tech.munidigital.lavadero.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.munidigital.lavadero.dto.request.VehiculoRequestDTO;
import tech.munidigital.lavadero.dto.response.VehiculoResponseDTO;
import tech.munidigital.lavadero.entity.Vehiculo;

@Mapper(componentModel = "spring")
public interface VehiculoMapper {

  @Mapping(target = "clienteId", source = "cliente.id")
  VehiculoResponseDTO toDto(Vehiculo vehiculo);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "cliente", ignore = true)
  @Mapping(target = "turnos", ignore = true)
  Vehiculo toEntity(VehiculoRequestDTO dto);

}