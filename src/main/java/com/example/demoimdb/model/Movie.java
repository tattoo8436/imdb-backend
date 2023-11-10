package com.example.demoimdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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
    @Column(name = "description", length = 1000)
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "trailer")
    private String trailer;
    @Column(name = "releaseDate")
    private String releaseDate;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "type")
    private Integer type;
    @Column(name = "endYear")
    private Integer endYear;
    @Column(name = "number_season")
    private Integer numberSeason;
    @Column(name = "number_vote")
    private Integer numberVote;
    @Column(name = "score")
    private Double score;
    @Column(name = "language")
    private String language;
    @OneToMany(mappedBy = "movie")
    private List<MovieGenre> listMovieGenres;
    @OneToMany(mappedBy = "movie")
    private List<MovieActor> listMovieActors;
    @OneToMany(mappedBy = "movie")
    private List<MovieDirector> listMovieDirectors;
    @OneToMany(mappedBy = "movie")
    private List<Comment> listComments;
    @JsonIgnore
    @OneToMany(mappedBy = "movie")
    private List<Rating> listRatings;
    @OneToMany(mappedBy = "movie")
    private List<Episode> listEpisodes;
}
