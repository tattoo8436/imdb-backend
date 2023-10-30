package com.example.demoimdb.dto.actor;

import com.example.demoimdb.dto.AccountAdmin;
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
public class SearchActorRequestDTO extends GetListByPageDTO {
    @NotNull(message = "Tài khoản admin là bắt buộc")
    private AccountAdmin accountAdmin;
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
