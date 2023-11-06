package com.example.demoimdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.security.auth.Subject;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {
    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "episode_id")
    @JsonIgnore
    private Episode episode;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content", length = 1000)
    private String content;
    @Column(name = "date")
    private String date;
}
