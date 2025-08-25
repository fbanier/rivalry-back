package org.example.rivalry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rivalry.entity.Game;
import org.example.rivalry.entity.News;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NewsDto {
    private String name;
    private String preamble;
    private String text;
    private String image;

    private String date;

    private Boolean isActive;

    public News dtoToEntity () {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return News.builder()
                .name(getName())
                .preamble(getPreamble())
                .text(getText())
                .image(getImage())
                .date(LocalDate.parse(getDate(),dateTimeFormatter))
                .isActive(getIsActive())
                .build();
    }

}
