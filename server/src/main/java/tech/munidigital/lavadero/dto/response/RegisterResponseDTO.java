package tech.munidigital.lavadero.dto.response;

import lombok.*;

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