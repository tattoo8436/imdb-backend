package com.example.demoimdb.dto.actor;

import com.example.demoimdb.dto.account.BaseAccountDTO;
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
public class DeleteActorRequestDTO extends BaseAccountDTO {
    @NotNull(message = "ID là bắt buộc!")
    @NotBlank(message = "ID không được để trống!")
    private Long id;
}
