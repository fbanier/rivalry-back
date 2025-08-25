package org.example.rivalry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String lastName;
    private String firstName;
    private String username;
    private String dateOfBirth;
    private String dateOfCreation;
    private String avatar;


}
