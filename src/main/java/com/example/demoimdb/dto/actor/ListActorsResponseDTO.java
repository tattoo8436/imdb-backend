package com.example.demoimdb.dto.actor;

import com.example.demoimdb.dto.GetListByPageDTO;
import com.example.demoimdb.model.Actor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListActorsResponseDTO extends GetListByPageDTO {
    private Long totalRecords;
    private List<ActorResponseDTO> listActors;
}
