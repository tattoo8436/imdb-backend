package com.example.demoimdb.dto;

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
public class AccountAdmin {
    @NotNull(message = "Tên đăng nhập là bắt buộc!")
    private String username;
    @NotNull(message = "Mật khẩu là bắt buộc!")
    private String password;
}
