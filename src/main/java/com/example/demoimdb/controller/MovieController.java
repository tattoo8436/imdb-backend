package com.example.demoimdb.controller;

import com.example.demoimdb.dto.movie.ListMoviesResponseDTO;
import com.example.demoimdb.dto.movie.MovieRequestDTO;
import com.example.demoimdb.dto.movie.SearchMovieRequestDTO;
import com.example.demoimdb.model.Movie;
import com.example.demoimdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @PostMapping("/search")
    public ResponseEntity<ListMoviesResponseDTO> searchMovie(@RequestBody SearchMovieRequestDTO searchMovieRequestDTO){
        return ResponseEntity.ok(movieService.searchMovie(searchMovieRequestDTO));
    }

    @PostMapping("/get")
    public ResponseEntity<Movie> getMovieById(@Valid @RequestBody MovieRequestDTO movieRequestDTO){
        return ResponseEntity.ok(movieService.getMovieById(movieRequestDTO));
    }

    @PostMapping("")
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO){
        return ResponseEntity.ok(movieService.addMovie(movieRequestDTO));
    }

    @PutMapping("")
    public ResponseEntity<Movie> editMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO){
        return ResponseEntity.ok(movieService.editMovie(movieRequestDTO));
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO){
        return ResponseEntity.ok(movieService.deleteMovie(movieRequestDTO));
    }

    @PostMapping("/add-season")
    public ResponseEntity<Integer> addSeason(@Valid @RequestBody MovieRequestDTO movieRequestDTO){
        return ResponseEntity.ok(movieService.addSeason(movieRequestDTO));
    }
}
