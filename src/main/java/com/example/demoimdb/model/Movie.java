package com.example.demoimdb.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "trailer")
    private String trailer;
    @Column(name = "releaseDate")
    private String releaseDate;
    @Column(name = "duration")
    private String duration;
    @Column(name = "type")
    private String type;
    @Column(name = "number_season")
    private Integer numberSeason;
    @Column(name = "number_vote")
    private Integer numberVote;
    @Column(name = "score")
    private Double score;
    @OneToMany(mappedBy = "movie")
    private List<MovieGenre> listMovieGenres;
    @OneToMany(mappedBy = "movie")
    private List<MovieActor> listMovieActors;
    @OneToMany(mappedBy = "movie")
    private List<MovieDirector> listMovieDirectors;
    @OneToMany(mappedBy = "movie")
    private List<Comment> listComments;
    @OneToMany(mappedBy = "movie")
    private List<Rating> listRatings;
    @OneToMany(mappedBy = "movie")
    private List<Episode> listEpisodes;
}
