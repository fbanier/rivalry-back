package org.example.rivalry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.dto.TournamentDto;
import org.example.rivalry.enums.TournamentFormat;
import org.example.rivalry.enums.TournamentStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private LocalDateTime beginDate;
    private LocalDateTime endDate;

    private TournamentFormat format;
    private Integer numberOfPlayers;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "game_id")
    private Game game;

    private TournamentStatus status;

    private Boolean isActive;

    public TournamentDto entityToDto (){
        return TournamentDto.builder()
                .id(getId())
                .name(getName())
                .description(getDescription())
                .beginDate(getBeginDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .endDate(getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .format(getFormat().toString())
                .status(getStatus().toString())
                .numberOfPlayers(getNumberOfPlayers())
                .game(getGame().getId())
                .isActive(getIsActive())
                .build();
    }
}
