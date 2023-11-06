package com.example.demoimdb.repository;

import com.example.demoimdb.model.Actor;
import com.example.demoimdb.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("select r from Rating r where r.account.id = :accountId and r.movie.id = :movieId")
    Rating getRatingMovieByAccount(@Param("accountId") Long accountId, @Param("movieId") Long movieId);
}
