package tech.munidigital.lavadero.dto.response;

import lombok.*;

@Setter
@Getter
@ToString
public class LoginResponseDTO {

    private Long id;
    private String token;
    private String email;
    private String firstName;
    private String lastName;

    public static LoginResponseDTO of(Long id, String email, String firstName, String lastName, String token) {
        return new LoginResponseDTO(id, email, firstName, lastName, token);
    }

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(Long id, String email, String firstName, String lastName, String token) {
        this.id = id;
        this.token = token;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // ---- Getters y Setters ---------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}