package tech.munidigital.lavadero.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class LoginResponseDTO {

  private Long id;
  private String email;
  private String firstName;
  private String lastName;
  private String token;

}