package tech.munidigital.lavadero.service;

import tech.munidigital.lavadero.dto.request.LoginRequestDTO;
import tech.munidigital.lavadero.dto.response.LoginResponseDTO;

public interface LoginService {

  LoginResponseDTO login(LoginRequestDTO requestDTO);

}
