package com.example.demoimdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "movie_director")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieDirector {
    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
