package com.example.demoimdb.dto.genre;

import com.example.demoimdb.dto.AccountAdmin;
import com.example.demoimdb.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GenreRequestDTO extends Genre {
    @NotNull(message = "Tài khoản admin là bắt buộc")
    private AccountAdmin accountAdmin;

}
