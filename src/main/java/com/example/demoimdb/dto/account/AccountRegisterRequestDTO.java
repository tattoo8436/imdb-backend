package com.example.demoimdb.dto.account;

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
public class AccountRegisterRequestDTO extends BaseAccountDTO{
    @NotNull(message = "Email là bắt buộc!")
    @NotBlank(message = "Email không được để trống!")
    private String email;
}
