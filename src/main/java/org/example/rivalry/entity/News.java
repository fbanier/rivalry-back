package org.example.rivalry.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.enums.Roles;

import java.time.LocalDate;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String preamble;
    private String text;

    private LocalDate date;
}
