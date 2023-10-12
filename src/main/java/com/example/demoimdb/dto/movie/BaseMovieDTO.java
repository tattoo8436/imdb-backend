package com.example.demoimdb.dto.movie;

import com.example.demoimdb.dto.director.DirectorResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class BaseMovieDTO {
    private String name;
    private String description;
    private String image;
    private String trailer;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Ngày phát hành không đúng định dạng YYYY-MM-DD!")
    private String releaseDate;
    private Integer duration;
    private Integer type;
    private Boolean ended;
    private Integer numberSeason;
    private Integer numberVote;
    private Double score;
    private String language;
    private DirectorResponseDTO directorResponseDTO;
}
