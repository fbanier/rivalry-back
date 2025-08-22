package org.example.rivalry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.entity.UserPlayer;
import org.example.rivalry.enums.Roles;
import org.example.rivalry.util.Password;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPlayerReceiveDto {

    private String firstName;
    private String lastName;

    @NotNull(message = "The email is required")
    @NotBlank(message = "The email is required")
    @Pattern(regexp = "^[a-zA-Z0–9._%+-]+@[a-zA-Z0–9.-]+\\.[a-zA-Z]{2,}$" , message = "The email address must be valid")
    private String email;

    @Pattern(regexp = "[0-9]{2}[-|\\/]{1}[0-9]{2}[-|\\/]{1}[0-9]{4}" , message = "The date must be in the format : dd-MM-yyyy")
    private String dateOfBirth;

    @NotNull(message = "The password is required")
    @NotBlank(message = "The password is required")
    @Size(min = 8, message = "The password must be 8 characters minimum")
    private String password;

    @Size(min = 4, message = "The username must be 4 characters minimum")
    private String username;

    private String role;
    private String avatar;


    public UserPlayer dtoToEntity (){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Roles roleEnum;
        try{
            roleEnum = Roles.valueOf(role);
        } catch (Exception e){
            roleEnum = Roles.valueOf("PLAYER");
        }

        return UserPlayer.builder()
                .firstName(getFirstName())
                .lastName(getLastName())
                .lastName(getLastName())
                .email(getEmail())
                .dateOfBirth(LocalDate.parse(getDateOfBirth(),dateTimeFormatter))
                .dateOfCreation(LocalDate.now())
                .password(Password.hashPassword(getPassword()))
                .role(roleEnum)
                .avatar(getAvatar())
                .build();
    }


}
