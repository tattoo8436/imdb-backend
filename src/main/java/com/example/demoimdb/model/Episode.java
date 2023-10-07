package com.example.demoimdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "episode")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Episode {
    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "releaseDate")
    private String releaseDate;
    @Column(name = "duration")
    private String duration;
    @Column(name = "number_vote")
    private Integer numberVote;
    @Column(name = "score")
    private Double score;
    @OneToMany(mappedBy = "episode")
    private List<EpisodeGenre> listEpisodeGenres;
    @OneToMany(mappedBy = "episode")
    private List<EpisodeActor> listEpisodeActors;
    @OneToMany(mappedBy = "episode")
    private List<Comment> listComments;
    @OneToMany(mappedBy = "episode")
    private List<Rating> listRatings;
}
