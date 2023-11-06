package com.example.demoimdb.repository;

import com.example.demoimdb.model.Actor;
import com.example.demoimdb.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("select distinct m from Movie m join m.listMovieGenres mg join mg.genre g where " +
            "(:genreId is null or g.id = :genreId) " +
            "and (:name is null or lower(m.name) like %:name%) " +
            "and (:type is null or m.type = :type) " +
            "and (:score is null or m.score > :score) " +
            "and (:releaseDate is null or m.releaseDate >= :releaseDate) " +
            "and (:language is null or m.language = :language)")
    Page<Movie> searchMovie(@Param("genreId") Long genreId, @Param("name") String name, @Param("type") Integer type,
                            @Param("score") Integer score, @Param("releaseDate") String releaseDate,
                            @Param("language") String language, Pageable pageable);

    @Query("select distinct m from Movie m join m.listMovieActors ma join ma.actor a where a.id = :actorId")
    List<Movie> getListMoviesByActor(@Param("actorId") Long actorId);

    @Query("select distinct m from Movie m join m.listMovieDirectors md join md.director d where d.id = :directorId")
    List<Movie> getListMoviesByDirector(@Param("directorId") Long directorId);
}
