package com.example.demoimdb.repository;

import com.example.demoimdb.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("select m from Movie m join m.listMovieGenres mg join mg.genre g where (:genreId is null or g.id = :genreId) " +
            "and (:name is null or lower(m.name) like %:name%)")
    List<Movie> searchMovie(@Param("genreId") Long genreId, @Param("name") String name);
}
