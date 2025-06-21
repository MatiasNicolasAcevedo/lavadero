package tech.munidigital.lavadero.service;

import tech.munidigital.lavadero.dto.request.CobroRequestDTO;
import tech.munidigital.lavadero.dto.response.CobroResponseDTO;

public interface CobroService {

  CobroResponseDTO createCobro(CobroRequestDTO cobroRequestDTO);

}
