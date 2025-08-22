package org.example.rivalry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "playerone_id")
    private UserPlayer playerOne;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "playertwo_id")
    private UserPlayer playerTwo;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "winner_id")
    private UserPlayer winner;

    private LocalDateTime matchDate;
}
