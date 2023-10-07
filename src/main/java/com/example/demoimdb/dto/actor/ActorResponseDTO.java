package com.example.demoimdb.dto.actor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActorResponseDTO extends BaseActorDTO{
    private String image;
}