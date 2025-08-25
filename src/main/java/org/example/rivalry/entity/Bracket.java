package org.example.rivalry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.dto.BracketDto;
import org.example.rivalry.dto.TournamentDto;

import java.time.format.DateTimeFormatter;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Bracket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    private String name;

    private Boolean isActive;

    public BracketDto entityToDto (){
        return BracketDto.builder()
                .name(getName())
                .tournament(getTournament().getId())
                .isActive(getIsActive())
                .build();
    }
}
