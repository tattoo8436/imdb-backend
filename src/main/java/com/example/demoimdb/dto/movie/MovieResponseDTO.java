package com.example.demoimdb.dto.movie;

import com.example.demoimdb.dto.actor.ActorResponseDTO;
import com.example.demoimdb.dto.genre.GenreResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieResponseDTO extends BaseMovieDTO {
    private Long id;
    private List<MovieGenreResponseDTO> listMovieGenres;
    private List<ActorResponseDTO> listMovieActors;
}
