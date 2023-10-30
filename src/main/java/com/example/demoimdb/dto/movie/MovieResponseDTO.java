package com.example.demoimdb.dto.movie;

import com.example.demoimdb.model.Genre;
import com.example.demoimdb.model.Movie;

import java.util.List;

public class MovieResponseDTO extends Movie {
    private List<GenreInMovieDTO> listMovieGenres;
}
