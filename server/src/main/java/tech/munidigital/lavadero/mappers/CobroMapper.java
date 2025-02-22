package tech.munidigital.lavadero.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.munidigital.lavadero.dto.request.CobroRequestDTO;
import tech.munidigital.lavadero.dto.response.CobroResponseDTO;
import tech.munidigital.lavadero.entity.Cobro;

@Mapper(componentModel = "spring")
public interface CobroMapper {

    @Mapping(target = "turnoId", source = "turno.id")
    CobroResponseDTO toDto(Cobro cobro);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turno", ignore = true)
    Cobro toEntity(CobroRequestDTO dto);

}