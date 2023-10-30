package com.example.demoimdb.dto.movie;

import com.example.demoimdb.dto.AccountAdmin;
import com.example.demoimdb.model.Movie;
import com.example.demoimdb.model.MovieActor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieRequestDTO extends Movie {
    private AccountAdmin accountAdmin;
    private List<Long> listDirectorIds;
    private List<Long> listGenreIds;
    private List<MovieActor> listActors;
}
