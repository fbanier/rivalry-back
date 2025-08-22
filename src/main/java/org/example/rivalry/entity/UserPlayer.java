package org.example.rivalry.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.dto.UserPlayerAuthResponseDto;
import org.example.rivalry.dto.UserPlayerResponseDto;
import org.example.rivalry.enums.Roles;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    private LocalDate dateOfBirth;
    private LocalDate dateOfCreation;

    private Roles role;
    private String avatar;

    public UserPlayerResponseDto entityToPublicDto() {
        return UserPlayerResponseDto.builder()
                .username(getUsername())
                .avatar(getAvatar())
                .build();
    }

    public UserPlayerAuthResponseDto entityToDto() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return UserPlayerAuthResponseDto.builder()
                .username(getUsername())
                .avatar(getAvatar())
                .firstName(getLastName())
                .lastName(getFirstName())
                .username(getUsername())
                .email(getEmail())
                .dateOfBirth(getDateOfBirth().format(formatter))
                .dateOfCreation(getDateOfCreation().format(formatter))
                .role(getRole().toString())
                .build();
    }
}
