package com.example.demoimdb.dto.director;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class BaseDirectorDTO {
    private String name;
    private String description;
    private String image;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Ngày sinh không đúng định dạng YYYY-MM-DD!")
    private String dob;
}
