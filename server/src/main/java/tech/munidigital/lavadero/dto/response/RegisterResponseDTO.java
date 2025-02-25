package tech.munidigital.lavadero.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class RegisterResponseDTO {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String token;

    public static RegisterResponseDTO of(Long id, String firstName, String lastName, String email, String phone, String token) {
        return new RegisterResponseDTO(id, firstName, lastName, email, phone, token);
    }

    public RegisterResponseDTO() {
    }

    public RegisterResponseDTO(Long id, String email, String firstName, String lastName, String phone, String token) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.token = token;
    }

    // ---- Getters y Setters ---------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}