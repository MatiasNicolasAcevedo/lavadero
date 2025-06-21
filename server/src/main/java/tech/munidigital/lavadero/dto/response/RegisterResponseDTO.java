package tech.munidigital.lavadero.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class RegisterResponseDTO {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String token;

}