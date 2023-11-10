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
import java.util.List;

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
        Rating rating = getRatingMovieByAccount(ratingRequestDTO);
        try {
            if (ratingRequestDTO.getMovieId() != null) {
                Movie movie = movieRepository.findById(ratingRequestDTO.getMovieId()).get();
                double newScore = 0;
                if (rating != null) {
                    newScore = (movie.getScore() * movie.getNumberVote() - rating.getScore() + ratingRequestDTO.getScore()) / movie.getNumberVote();
                    movie.setScore(newScore);
                } else {
                    newScore = (movie.getScore() * movie.getNumberVote() + ratingRequestDTO.getScore()) / (movie.getNumberVote() + 1);
                    movie.setScore(newScore);
                    movie.setNumberVote(movie.getNumberVote() + 1);
                }
                movieSaved = movieRepository.save(movie);
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
                return ratingRepository.save(new Rating(account, movieSaved, null, null, ratingRequestDTO.getScore(), dateStr));
            }
        } catch (Exception e) {
            throw new ApiInputException("Đánh giá thất bại");
        }
    }

    public Rating addRatingEpisode(RatingRequestDTO ratingRequestDTO) {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(ratingRequestDTO.getAccountAdmin().getUsername(), ratingRequestDTO.getAccountAdmin().getPassword());
        accountService.checkUser(baseAccountDTO);
        Episode episodeSaved = null;
        Rating rating = getRatingEpisodeByAccount(ratingRequestDTO);
        try {
            if (ratingRequestDTO.getEpisodeId() != null) {
                Episode episode = episodeRepository.findById(ratingRequestDTO.getEpisodeId()).get();
                Movie movie = movieRepository.findById(ratingRequestDTO.getMovieId()).get();
                double newScore = 0;
                double newScoreMovie = 0;
                if (rating != null) {
                    newScore = (episode.getScore() * episode.getNumberVote() - rating.getScore() + ratingRequestDTO.getScore()) / episode.getNumberVote();
                    episode.setScore(newScore);

                    newScoreMovie = (movie.getScore() * movie.getNumberVote() - rating.getScore() + ratingRequestDTO.getScore()) / movie.getNumberVote();
                    movie.setScore(newScoreMovie);
                } else {
                    newScore = (episode.getScore() * episode.getNumberVote() + ratingRequestDTO.getScore()) / (episode.getNumberVote() + 1);
                    episode.setScore(newScore);
                    episode.setNumberVote(episode.getNumberVote() + 1);

                    newScoreMovie = (movie.getScore() * movie.getNumberVote() + ratingRequestDTO.getScore()) / (movie.getNumberVote() + 1);
                    movie.setScore(newScoreMovie);
                    movie.setNumberVote(movie.getNumberVote() + 1);
                }
                episodeSaved = episodeRepository.save(episode);
                movieRepository.save(movie);
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
                return ratingRepository.save(new Rating(account, null, episodeSaved, null, ratingRequestDTO.getScore(), dateStr));
            }
        } catch (Exception e) {
            throw new ApiInputException("Đánh giá thất bại");
        }
    }

    public Rating getRatingMovieByAccount(RatingRequestDTO ratingRequestDTO) {
        if (ratingRequestDTO.getAccountAdmin().getUsername() == null) {
            return null;
        }
        Rating rating = ratingRepository.getRatingMovieByAccount(
                accountRepository.findByUsername(ratingRequestDTO.getAccountAdmin().getUsername()).getId(),
                ratingRequestDTO.getMovieId());
        return rating;
    }

    public Rating getRatingEpisodeByAccount(RatingRequestDTO ratingRequestDTO) {
        if (ratingRequestDTO.getAccountAdmin().getUsername() == null) {
            return null;
        }
        Rating rating = ratingRepository.getRatingEpisodeByAccount(
                accountRepository.findByUsername(ratingRequestDTO.getAccountAdmin().getUsername()).getId(),
                ratingRequestDTO.getEpisodeId());
        return rating;
    }

    public int[] getStatisticMovie(Long movieId) {
        List<Rating> listRatings = ratingRepository.getRatingByMovie(movieId);
        int[] arr = new int[10];
        for (int i = 0; i < listRatings.size(); i++) {
            arr[listRatings.get(i).getScore() - 1] += 1;
        }
        return arr;
    }

}
