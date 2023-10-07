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
    @OneToMany(mappedBy = "director")
    private List<MovieDirector> listMovieDirectors;
}
