package com.example.demoimdb.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "director")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "dob")
    private String dob;
    @OneToMany(mappedBy = "director")
    private List<Movie> listMovies;
    @OneToMany(mappedBy = "director")
    private List<Episode> listEpisodes;
}
