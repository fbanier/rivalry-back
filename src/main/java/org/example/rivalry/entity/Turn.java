package org.example.rivalry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.dto.BracketDto;
import org.example.rivalry.dto.TurnDto;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.repository.TournamentRepository;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Turn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "bracket_id")
    private Bracket bracket;

    private Long points;

    private Boolean isActive;

    public TurnDto entityToDto (){
        return TurnDto.builder()
                .bracket(getBracket().getId())
                .points(getPoints())
                .isActive(getIsActive())
                .build();
    }
}
