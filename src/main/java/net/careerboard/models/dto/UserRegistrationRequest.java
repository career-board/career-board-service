package net.careerboard.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import net.careerboard.models.Role;

@Data
public class UserRegistrationRequest {
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email address")
    private String email;

    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @NotNull(message = "Password cannot be null")
    private String password;

    @NotNull(message = "Role cannot be null")
    private Role role;

    private String currentCompany;
}
