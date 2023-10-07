package com.example.demoimdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.security.auth.Subject;

@Entity
@Table(name = "episode_genre")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EpisodeGenre {
    @ManyToOne
    @JoinColumn(name = "genre_id")
    @JsonIgnore
    private Genre genre;
    @ManyToOne
    @JoinColumn(name = "episode_id")
    @JsonIgnore
    private Episode episode;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
