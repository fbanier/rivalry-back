package org.example.rivalry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.enums.Roles;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserPlayerAuthResponseDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String avatar;
    private String dateOfBirth;
    private String dateOfCreation;
    private String role;
}
