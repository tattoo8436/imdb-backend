package com.example.demoimdb.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountLoginResponseDTO extends BaseAccountDTO{
    private Long id;
    private String role;
}
