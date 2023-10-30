package com.example.demoimdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.security.auth.Subject;

@Entity
@Table(name = "movie_genre")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieGenre {
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
