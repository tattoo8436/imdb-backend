package com.example.demoimdb.repository;

import com.example.demoimdb.model.Actor;
import com.example.demoimdb.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("select r from Rating r where r.account.id = :accountId and r.movie.id = :movieId")
    Rating getRatingMovieByAccount(@Param("accountId") Long accountId, @Param("movieId") Long movieId);

    @Query("select r from Rating r where r.movie.id = :movieId")
    List<Rating> getRatingByMovie(@Param("movieId") Long movieId);

    @Query("select r from Rating r where r.account.id = :accountId and r.episode.id = :episodeId")
    Rating getRatingEpisodeByAccount(@Param("accountId") Long accountId, @Param("episodeId") Long episodeId);

    @Query("select r from Rating r where r.date > :date")
    List<Rating> getRatingNew(@Param("date") String date);
}
