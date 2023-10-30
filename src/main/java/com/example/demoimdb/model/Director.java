package com.example.demoimdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "description", length = 1000)
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "dob")
    private String dob;
    @JsonIgnore
    @OneToMany(mappedBy = "director")
    private List<MovieDirector> listMovieDirectors;
}
