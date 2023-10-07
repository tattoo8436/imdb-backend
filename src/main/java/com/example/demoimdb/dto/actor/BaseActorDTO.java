package com.example.demoimdb.dto.actor;

import com.example.demoimdb.dto.account.BaseAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class BaseActorDTO {
    private BaseAccountDTO baseAccountDTO;
    private String name;
    private String description;
    private String dob;
}
