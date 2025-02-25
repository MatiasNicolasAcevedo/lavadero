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

    // ---- Getters y Setters ---------------------------------------

    public @NotBlank(message = "Email is required.") @Email(message = "Invalid email address.") @Size(max = 50, message = "Email must not exceed 50 characters ") @Pattern(regexp = ".+@.+\\.[a-zA-Z]{2,}", message = "Email should have a valid domain with at least two characters") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required.") @Email(message = "Invalid email address.") @Size(max = 50, message = "Email must not exceed 50 characters ") @Pattern(regexp = ".+@.+\\.[a-zA-Z]{2,}", message = "Email should have a valid domain with at least two characters") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password is required.") @Size(min = 6, message = "Password must be at least 6 characters.") @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain at least one uppercase letter, one number, and one special character."
    ) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required.") @Size(min = 6, message = "Password must be at least 6 characters.") @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain at least one uppercase letter, one number, and one special character."
    ) String password) {
        this.password = password;
    }

    public @NotBlank(message = "First name is required.") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "First name should only contain letters and spaces") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First name is required.") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "First name should only contain letters and spaces") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Last name is required.") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Last name should only contain letters and spaces") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Last name is required.") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Last name should only contain letters and spaces") String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank(message = "Phone is required.") @Pattern(regexp = "^[0-9\\-\\+]{9,15}$", message = "Phone number is invalid") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "Phone is required.") @Pattern(regexp = "^[0-9\\-\\+]{9,15}$", message = "Phone number is invalid") String phone) {
        this.phone = phone;
    }

}