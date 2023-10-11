package com.example.demoimdb.dto.genre;

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
public class GenreRequestDTO extends BaseGenreDTO {
    @NotNull(message = "Tên đăng nhập là bắt buộc!")
    @NotBlank(message = "Tên đăng nhập không được để trống!")
    private String username;
    @NotNull(message = "Mật khẩu là bắt buộc!")
    @NotBlank(message = "Mật khẩu không được để trống!")
    private String password;
    private Long id;

}
