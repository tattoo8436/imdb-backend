package com.example.demoimdb.service;

import com.example.demoimdb.model.Movie;
import com.example.demoimdb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    public List<Movie> searchMovie(Long genreId){
        return movieRepository.searchMovie(genreId);
    }
}
