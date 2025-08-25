package org.example.rivalry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.dto.UserDto;
import org.example.rivalry.dto.UserPublicDto;
import org.example.rivalry.enums.Roles;

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
    private Long id_user;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String username;
    private String password;

    private LocalDate dateOfBirth;
    private LocalDate dateOfCreation;

    private Roles role;
    private String avatar;

    public UserPlayer(String email, String username, String password, int role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role == 0 ? Roles.PLAYER : Roles.ADMIN;
    }

    public UserDto entityToDto (){
        return UserDto.builder()
                .id(getId_user())
                .email(getEmail())
                .firstName(getFirstName())
                .lastName(getLastName())
                .username(getUsername())
                .avatar(getAvatar())
                .dateOfBirth(getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .dateOfCreation(getDateOfCreation().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
    }

    public UserPublicDto entityToPublicDto (){
        return UserPublicDto.builder()
                .username(getUsername())
                .avatar(getAvatar())
                .build();
    }
}
