package com.example.demoimdb.dto.actor;

import com.example.demoimdb.dto.AccountAdmin;
import com.example.demoimdb.dto.account.BaseAccountDTO;
import com.example.demoimdb.model.Actor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActorRequestDTO extends Actor {
    @NotNull(message = "Tài khoản admin là bắt buộc")
    private AccountAdmin accountAdmin;

}
