package org.example.rivalry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.enums.TournamentFormat;
import org.example.rivalry.enums.TournamentStatus;

import java.time.LocalDateTime;

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
}
