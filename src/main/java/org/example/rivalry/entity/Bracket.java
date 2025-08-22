package org.example.rivalry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
