package tech.munidigital.lavadero.service;

import tech.munidigital.lavadero.dto.request.RegisterRequestDTO;
import tech.munidigital.lavadero.dto.response.RegisterResponseDTO;

public interface RegisterService {

    RegisterResponseDTO register(RegisterRequestDTO requestDTO);

}
