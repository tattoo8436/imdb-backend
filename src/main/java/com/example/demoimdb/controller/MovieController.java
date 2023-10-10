package com.example.demoimdb.controller;

import com.example.demoimdb.model.Genre;
import com.example.demoimdb.model.Movie;
import com.example.demoimdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @PostMapping("/search")
    public ResponseEntity<List<Movie>> searchMovie(@RequestBody Genre genre){
        return ResponseEntity.ok(movieService.searchMovie(genre.getId()));
    }
}
