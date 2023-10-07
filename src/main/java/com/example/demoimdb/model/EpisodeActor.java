package com.example.demoimdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.security.auth.Subject;

@Entity
@Table(name = "episode_actor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EpisodeActor {
    @ManyToOne
    @JoinColumn(name = "actor_id")
    @JsonIgnore
    private Actor actor;
    @ManyToOne
    @JoinColumn(name = "episode_id")
    @JsonIgnore
    private Episode episode;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
