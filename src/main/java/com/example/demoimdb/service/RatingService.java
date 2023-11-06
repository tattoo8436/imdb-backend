package com.example.demoimdb.service;

import com.example.demoimdb.dto.account.BaseAccountDTO;
import com.example.demoimdb.dto.rating.RatingRequestDTO;
import com.example.demoimdb.exception.ApiInputException;
import com.example.demoimdb.model.*;
import com.example.demoimdb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Rating addRatingMovie(RatingRequestDTO ratingRequestDTO) {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(ratingRequestDTO.getAccountAdmin().getUsername(), ratingRequestDTO.getAccountAdmin().getPassword());
        accountService.checkUser(baseAccountDTO);
        Movie movieSaved = null;
        Episode episode = null;
        Rating rating = getRatingMovieByAccount(ratingRequestDTO);
        try {
            if (ratingRequestDTO.getMovieId() != null) {
                Movie movie = movieRepository.findById(ratingRequestDTO.getMovieId()).get();
                double newScore = 0;
                if (rating != null) {
                    newScore = (movie.getScore()*movie.getNumberVote()-rating.getScore()+ratingRequestDTO.getScore())/movie.getNumberVote();
                    movie.setScore(newScore);
                } else {
                    newScore = (movie.getScore() * movie.getNumberVote() + ratingRequestDTO.getScore()) / (movie.getNumberVote() + 1);
                    movie.setScore(newScore);
                    movie.setNumberVote(movie.getNumberVote() + 1);
                }
                movieSaved = movieRepository.save(movie);
            }
            if (ratingRequestDTO.getEpisodeId() != null) {
                episode = episodeRepository.findById(ratingRequestDTO.getEpisodeId()).get();
            }
            Account account = accountRepository.findByUsername(ratingRequestDTO.getAccountAdmin().getUsername());
            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateStr = dateFormat.format(date);

            if (rating != null) {
                rating.setScore(ratingRequestDTO.getScore());
                rating.setDate(dateStr);
                return ratingRepository.save(rating);
            } else {
                return ratingRepository.save(new Rating(account, movieSaved, episode, null, ratingRequestDTO.getScore(), dateStr));
            }
        } catch (Exception e) {
            throw new ApiInputException("Đánh giá thất bại");
        }
    }

    public Rating getRatingMovieByAccount(RatingRequestDTO ratingRequestDTO) {
        if(ratingRequestDTO.getAccountAdmin().getUsername() == null){
            return null;
        }
        Rating rating = ratingRepository.getRatingMovieByAccount(
                accountRepository.findByUsername(ratingRequestDTO.getAccountAdmin().getUsername()).getId(),
                ratingRequestDTO.getMovieId());
        return rating;
    }

}
