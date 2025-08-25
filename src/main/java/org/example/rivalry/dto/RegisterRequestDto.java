package org.example.rivalry.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    @NotNull(message = "The email is required")
    @NotBlank(message = "The email is required")
    @Pattern(regexp = "^[a-zA-Z0–9._%+-]+@[a-zA-Z0–9.-]+\\.[a-zA-Z]{2,}$" , message = "The email address must be valid")
    private String email;

    @NotNull(message = "The username is required")
    @NotBlank(message = "The username is required")
    @Size(min = 4, message = "The username must be 4 characters minimum")
    private String username;

    @NotNull(message = "The password is required")
    @NotBlank(message = "The password is required")
    @Size(min = 8, message = "The password must be 8 characters minimum")
    private String password;

    private int role;

    private Boolean active;
}
