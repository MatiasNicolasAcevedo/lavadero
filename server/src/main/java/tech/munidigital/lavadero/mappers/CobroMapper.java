package tech.munidigital.lavadero.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import tech.munidigital.lavadero.dto.request.CobroRequestDTO;
import tech.munidigital.lavadero.dto.response.CobroResponseDTO;
import tech.munidigital.lavadero.entity.Cobro;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CobroMapper {

  @Named("toDto")
  @Mapping(target = "turnoId", expression = "java(cobro.getTurno() != null ? cobro.getTurno().getId() : null)")
  CobroResponseDTO toDto(Cobro cobro);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "turno", ignore = true)
  Cobro toEntity(CobroRequestDTO dto);

}