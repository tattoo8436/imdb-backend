package com.example.demoimdb.service;

import com.example.demoimdb.dto.movie.ListMoviesResponseDTO;
import com.example.demoimdb.dto.movie.MovieResponseDTO;
import com.example.demoimdb.dto.movie.SearchMovieRequestDTO;
import com.example.demoimdb.model.Movie;
import com.example.demoimdb.repository.MovieRepository;
import com.example.demoimdb.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    public ListMoviesResponseDTO searchMovie(SearchMovieRequestDTO searchMovieRequestDTO){
        List<Movie> listMovies = movieRepository.searchMovie(searchMovieRequestDTO.getGenreId(), searchMovieRequestDTO.getName());
        ListMoviesResponseDTO listMoviesResponseDTO = new ListMoviesResponseDTO();
        listMoviesResponseDTO.setListMovies(ConvertUtils.convertList(listMovies, MovieResponseDTO.class));
        return listMoviesResponseDTO;
    }
}
