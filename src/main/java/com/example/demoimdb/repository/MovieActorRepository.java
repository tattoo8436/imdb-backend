package com.example.demoimdb.repository;

import com.example.demoimdb.model.MovieActor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieActorRepository extends JpaRepository<MovieActor, Long> {
}
