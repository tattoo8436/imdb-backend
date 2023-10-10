package com.example.demoimdb.dto.director;

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
public class SearchDirectorRequestDTO extends GetListByPageDTO {
    @NotNull(message = "Tên đăng nhập là bắt buộc!")
    @NotBlank(message = "Tên đăng nhập không được để trống!")
    private String username;
    @NotNull(message = "Mật khẩu là bắt buộc!")
    @NotBlank(message = "Mật khẩu không được để trống!")
    private String password;
    private String sortBy;
    private String orderBy;
    private String name;

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
