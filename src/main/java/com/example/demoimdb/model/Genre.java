package com.example.demoimdb.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genre")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "genre")
    private List<MovieGenre> listMovieGenres;
    @OneToMany(mappedBy = "genre")
    private List<EpisodeGenre> listEpisodeGenres;
}
