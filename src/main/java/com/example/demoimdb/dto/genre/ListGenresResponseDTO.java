package com.example.demoimdb.dto.genre;

import com.example.demoimdb.dto.GetListByPageDTO;
import com.example.demoimdb.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListGenresResponseDTO {
    private Long totalRecords;
    private List<Genre> listGenres;
}
