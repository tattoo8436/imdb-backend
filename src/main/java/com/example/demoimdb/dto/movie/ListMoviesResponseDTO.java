package com.example.demoimdb.dto.movie;

import com.example.demoimdb.dto.GetListByPageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListMoviesResponseDTO extends GetListByPageDTO {
    private Long totalRecords;
    private List<MovieResponseDTO> listMovies;
}
