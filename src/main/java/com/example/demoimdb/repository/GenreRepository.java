package com.example.demoimdb.repository;

import com.example.demoimdb.model.Actor;
import com.example.demoimdb.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("select g from Genre g where :name is null or lower(g.name) like %:name%")
    Page<Genre> searchGenre(String name, Pageable pageable);

    boolean existsByName(String name);

    Genre findByName(String name);
}
