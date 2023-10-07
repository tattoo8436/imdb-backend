package com.example.demoimdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.security.auth.Subject;

@Entity
@Table(name = "movie_actor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieActor {
    @ManyToOne
    @JoinColumn(name = "actor_id")
    @JsonIgnore
    private Actor actor;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
