package com.example.demoimdb.dto.movie;

import com.example.demoimdb.dto.GetListByPageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchMovieRequestDTO extends GetListByPageDTO {
    private String sortBy;
    private String orderBy;
    private String name;
    private Long genreId;
    private Long actorId;
    private Integer type;
    private Integer score;
    private String language;

    @Override
    public void validateInput() {
        super.validateInput();
        if(sortBy == null){
            sortBy = "id";
        }
        if(orderBy == null){
            orderBy = "ASC";
        }
    }
}
