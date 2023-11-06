package com.example.demoimdb.controller;

import com.example.demoimdb.dto.rating.RatingRequestDTO;
import com.example.demoimdb.model.Rating;
import com.example.demoimdb.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping("/movie")
    public ResponseEntity<Rating> addRatingMovie(@Valid @RequestBody RatingRequestDTO ratingRequestDTO) {
        return ResponseEntity.ok(ratingService.addRatingMovie(ratingRequestDTO));
    }

    @PostMapping("/movie/get-by-account")
    public ResponseEntity<Rating> getRatingMovieByAccount(@RequestBody RatingRequestDTO ratingRequestDTO) {
        return ResponseEntity.ok(ratingService.getRatingMovieByAccount(ratingRequestDTO));
    }
}
