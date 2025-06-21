package tech.munidigital.lavadero.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.munidigital.lavadero.dto.request.CobroRequestDTO;
import tech.munidigital.lavadero.dto.response.CobroResponseDTO;
import tech.munidigital.lavadero.service.CobroService;

@Tag(
    name = "Cobros",
    description = "Operaciones relacionadas con la gestión de cobros del sistema Lavadero"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/cobros")
public class CobroController {

  private final CobroService cobroService;

  @Operation(
      summary = "Registrar un nuevo cobro",
      description = "Registra un nuevo cobro en el sistema asociado a un cliente y uno o más servicios. "
          + "Es necesario enviar los datos completos del cobro en el cuerpo de la solicitud."
  )
  @PostMapping
  public ResponseEntity<CobroResponseDTO> createCobro(@Valid @RequestBody CobroRequestDTO cobroRequestDTO) {
    CobroResponseDTO response = cobroService.createCobro(cobroRequestDTO);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

}