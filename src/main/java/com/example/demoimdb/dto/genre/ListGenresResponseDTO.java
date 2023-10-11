package com.example.demoimdb.dto.genre;

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
public class ListGenresResponseDTO extends GetListByPageDTO {
    private Long totalRecords;
    private List<GenreResponseDTO> listGenres;
}
