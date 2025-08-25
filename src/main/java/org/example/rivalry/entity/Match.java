package org.example.rivalry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.dto.BracketDto;
import org.example.rivalry.dto.MatchDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "turn_id")
    private Turn turn;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "playerone_id")
    private UserPlayer playerOne;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "playertwo_id")
    private UserPlayer playerTwo;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "winner_id")
    private UserPlayer winner;

    private LocalDateTime matchDate;

    private Boolean isActive;


    public MatchDto entityToDto (){
        return MatchDto.builder()
                .number(getNumber())
                .turn(getTurn().getId())
                .playerOne(getPlayerOne().getId_user())
                .playerTwo(getPlayerTwo().getId_user())
                .winner(getWinner().getId_user())
                .matchDate(getMatchDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .isActive(getIsActive())
                .build();
    }
}
