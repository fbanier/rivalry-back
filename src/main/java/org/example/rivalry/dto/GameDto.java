package org.example.rivalry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.entity.Bracket;
import org.example.rivalry.entity.Game;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.repository.TournamentRepository;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GameDto {
    private String title;
    private String description;
    private String image;

    private Boolean isActive;

    public Game dtoToEntity () {
        return Game.builder()
                .title(getTitle())
                .description(getDescription())
                .image(getImage())
                .isActive(getIsActive())
                .build();
    }

}
