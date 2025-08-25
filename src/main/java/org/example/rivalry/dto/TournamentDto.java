package org.example.rivalry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.entity.Tournament;
import org.example.rivalry.enums.TournamentFormat;
import org.example.rivalry.enums.TournamentStatus;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.repository.GameRepository;
import org.example.rivalry.util.AllowedValues;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TournamentDto {
    private Long id;

    private String name;
    private String description;

    @Pattern(regexp = "[0-9]{2}[-|\\/]{1}[0-9]{2}[-|\\/]{1}[0-9]{4}" , message = "The beginning date must be in the format : dd-MM-yyyy")
    private String beginDate;

    @Pattern(regexp = "[0-9]{2}[-|\\/]{1}[0-9]{2}[-|\\/]{1}[0-9]{4}" , message = "The date must be in the format : dd-MM-yyyy")
    private String endDate;

    @NotBlank(message = "The format must be \"SIMPLE\" or \"DOUBLE\"")
    @NotNull(message = "The format must be \"SIMPLE\" or \"DOUBLE\"")
    @AllowedValues(values = {"SIMPLE", "DOUBLE"}, message = "The format must be \"SIMPLE\" or \"DOUBLE\"")
    private String format;

    @Positive(message = "The number of players must be a positive value")
    @NotNull(message = "The number of players must be a positive value")
    private Integer numberOfPlayers;

    private Long game;

    @NotBlank(message = "The status must be \"OPEN\",\"CLOSED\", \"IN PROGRESS\" or \"FINISHED\"")
    @NotNull(message = "The status must be \"OPEN\",\"CLOSED\", \"IN PROGRESS\" or \"FINISHED\"")
    @AllowedValues(values = {"OPEN", "CLOSED", "IN PROGRESS", "FINISHED"}, message = "The status must be \"OPEN\",\"CLOSED\", \"IN PROGRESS\" or \"FINISHED\"")
    private String status;

    private Boolean isActive;

    public Tournament dtoToEntity (GameRepository gameRepository) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return Tournament.builder()
                .name(getName())
                .description(getDescription())
                .beginDate(LocalDateTime.parse(getBeginDate(),dateTimeFormatter))
                .endDate(LocalDateTime.parse(getEndDate(),dateTimeFormatter))
                .status(TournamentStatus.valueOf(getStatus()))
                .format(TournamentFormat.valueOf(getFormat()))
                .numberOfPlayers(getNumberOfPlayers())
                .game(gameRepository.findById(getGame()).orElseThrow(NotFoundException::new))
                .isActive(getIsActive())
                .build();
    }
}
