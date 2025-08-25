package org.example.rivalry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.entity.Bracket;
import org.example.rivalry.entity.Turn;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.repository.BracketRepository;
import org.example.rivalry.repository.TournamentRepository;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TurnDto {
    private Long bracket;

    private Long points;

    private Boolean isActive;

    public Turn dtoToEntity (BracketRepository bracketRepository) {
        return Turn.builder()
                .bracket(bracketRepository.findById(getBracket()).orElseThrow(NotFoundException::new))
                .points(getPoints())
                .isActive(getIsActive())
                .build();
    }

}
