package tech.munidigital.lavadero.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class RegisterRequestDTO {

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email address.")
    @Size(max = 50, message = "Email must not exceed 50 characters ")
    @Pattern(regexp = ".+@.+\\.[a-zA-Z]{2,}", message = "Email should have a valid domain with at least two characters")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, message = "Password must be at least 6 characters.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain at least one uppercase letter, one number, and one special character."
    )
    private String password;

    @NotBlank(message = "First name is required.")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "First name should only contain letters and spaces")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Last name should only contain letters and spaces")
    private String lastName;

    @NotBlank(message = "Phone is required.")
    @Pattern(regexp = "^[0-9\\-\\+]{9,15}$", message = "Phone number is invalid")
    private String phone;

}