package org.example.rivalry.dto;

import io.cucumber.java.it.Ma;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.entity.Match;
import org.example.rivalry.entity.Turn;
import org.example.rivalry.entity.UserPlayer;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.repository.BracketRepository;
import org.example.rivalry.repository.TurnRepository;
import org.example.rivalry.repository.UserPlayerRepository;
import org.example.rivalry.service.UserPlayerService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MatchDto {

    private Integer number;

    private Long turn;

    private Long playerOne;
    private Long playerTwo;
    private Long winner;

    @Pattern(regexp = "[0-9]{2}[-|\\/]{1}[0-9]{2}[-|\\/]{1}[0-9]{4}" , message = "The date must be in the format : dd-MM-yyyy")
    private String matchDate;

    private Boolean isActive;

    public Match dtoToEntity (TurnRepository turnRepository, UserPlayerRepository userPlayerRepository) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return Match.builder()
                .number(getNumber())
                .turn(turnRepository.findById(getTurn()).orElseThrow(NotFoundException::new))
                .playerOne(userPlayerRepository.findById(getPlayerOne()).orElseThrow(NotFoundException::new))
                .playerTwo(userPlayerRepository.findById(getPlayerTwo()).orElseThrow(NotFoundException::new))
                .winner(userPlayerRepository.findById(getWinner()).orElseThrow(NotFoundException::new))
                .matchDate(LocalDateTime.parse(getMatchDate(),dateTimeFormatter))
                .isActive(getIsActive())
                .build();
    }

}
