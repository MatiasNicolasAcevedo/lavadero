package tech.munidigital.lavadero.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.munidigital.lavadero.dto.request.TurnoRequestDTO;
import tech.munidigital.lavadero.dto.response.TurnoResponseDTO;
import tech.munidigital.lavadero.entity.Turno;

@Mapper(componentModel = "spring", uses = {CobroMapper.class})
public interface TurnoMapper {

    @Mapping(target = "vehiculoId", source = "vehiculo.id")
    @Mapping(target = "cobro", source = "cobro", qualifiedByName = "toDto")
    TurnoResponseDTO toDto(Turno turno);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehiculo", ignore = true)
    Turno toEntity(TurnoRequestDTO dto);

}