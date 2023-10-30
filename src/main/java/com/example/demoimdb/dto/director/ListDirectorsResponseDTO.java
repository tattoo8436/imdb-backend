package com.example.demoimdb.dto.director;

import com.example.demoimdb.dto.GetListByPageDTO;
import com.example.demoimdb.model.Director;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListDirectorsResponseDTO {
    private Long totalRecords;
    private List<Director> listDirectors;
}
