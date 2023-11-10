package com.example.demoimdb.controller;

import com.example.demoimdb.dto.movie.ListMoviesResponseDTO;
import com.example.demoimdb.dto.movie.MovieRequestDTO;
import com.example.demoimdb.dto.movie.SearchMovieRequestDTO;
import com.example.demoimdb.model.Movie;
import com.example.demoimdb.model.Rating;
import com.example.demoimdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping("/search")
    public ResponseEntity<ListMoviesResponseDTO> searchMovie(@RequestBody SearchMovieRequestDTO searchMovieRequestDTO) {
        return ResponseEntity.ok(movieService.searchMovie(searchMovieRequestDTO));
    }

    @GetMapping("")
    public ResponseEntity<Movie> getMovieById(@RequestParam Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PostMapping("")
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        return ResponseEntity.ok(movieService.addMovie(movieRequestDTO));
    }

    @PutMapping("")
    public ResponseEntity<Movie> editMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        return ResponseEntity.ok(movieService.editMovie(movieRequestDTO));
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        return ResponseEntity.ok(movieService.deleteMovie(movieRequestDTO));
    }

    @PostMapping("/add-season")
    public ResponseEntity<Integer> addSeason(@Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        return ResponseEntity.ok(movieService.addSeason(movieRequestDTO));
    }

    @GetMapping("/trending")
    public ResponseEntity<List<Long>> getTrendingMovie() {
        return ResponseEntity.ok(movieService.getTrendingMovie());
    }

    @GetMapping("/similar")
    public ResponseEntity<List<Movie>> getListMovieSimilar(@RequestParam Long id) {
        return ResponseEntity.ok(movieService.getListMoviesSimilar(id));
    }
}
