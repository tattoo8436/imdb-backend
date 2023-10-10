package com.example.demoimdb.repository;

import com.example.demoimdb.model.Actor;
import com.example.demoimdb.model.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    @Query("select d from Director d where :name is null or lower(d.name) like %:name%")
    Page<Director> searchDirector(String name, Pageable pageable);
}
