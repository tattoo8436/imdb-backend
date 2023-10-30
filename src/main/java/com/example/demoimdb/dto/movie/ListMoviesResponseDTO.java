package com.example.demoimdb.dto.movie;

import com.example.demoimdb.dto.GetListByPageDTO;
import com.example.demoimdb.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListMoviesResponseDTO {
    private Long totalRecords;
    private List<Movie> listMovies;
}
