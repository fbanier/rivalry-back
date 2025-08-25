package org.example.rivalry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.entity.Bracket;
import org.example.rivalry.entity.Tournament;
import org.example.rivalry.enums.TournamentFormat;
import org.example.rivalry.enums.TournamentStatus;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.repository.TournamentRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BracketDto {
    private Long tournament;

    private String name;

    private Boolean isActive;

    public Bracket dtoToEntity (TournamentRepository tournamentRepository) {
        return Bracket.builder()
                .name(getName())
                .tournament(tournamentRepository.findById(getTournament()).orElseThrow(NotFoundException::new))
                .isActive(getIsActive())
                .build();
    }

}
