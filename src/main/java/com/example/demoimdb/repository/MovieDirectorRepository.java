package com.example.demoimdb.repository;

import com.example.demoimdb.model.MovieActor;
import com.example.demoimdb.model.MovieDirector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDirectorRepository extends JpaRepository<MovieDirector, Long> {
}
