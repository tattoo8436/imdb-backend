package com.example.demoimdb.dto.actor;

import com.example.demoimdb.dto.account.BaseAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@NoArgsConstructor
@Getter
@Setter
public class ActorRequestDTO extends BaseActorDTO{
    private MultipartFile image;

    public ActorRequestDTO(BaseAccountDTO baseAccountDTO, String name, String description, String dob, MultipartFile image) {
        super(baseAccountDTO, name, description, dob);
        this.image = image;
    }
}
