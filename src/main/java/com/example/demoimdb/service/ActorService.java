package com.example.demoimdb.service;

import com.example.demoimdb.dto.account.BaseAccountDTO;
import com.example.demoimdb.dto.actor.ActorRequestDTO;
import com.example.demoimdb.dto.actor.ActorResponseDTO;
import com.example.demoimdb.model.Actor;
import com.example.demoimdb.repository.ActorRepository;
import com.example.demoimdb.utils.ConvertUtils;
import com.example.demoimdb.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private AccountService accountService;
    public List<ActorResponseDTO> getAllActors(){
        return ConvertUtils.convertList(actorRepository.findAll(), ActorResponseDTO.class);
    }

    public ActorResponseDTO saveActor(ActorRequestDTO actorRequestDTO) throws IOException {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(actorRequestDTO.getBaseAccountDTO().getUsername(), actorRequestDTO.getBaseAccountDTO().getPassword());
        accountService.checkAdmin(baseAccountDTO);
        String imageName = FileUtils.saveImage(actorRequestDTO.getImage());
        Actor actor = new Actor(null, actorRequestDTO.getName(), null, imageName,
                null, null, null);
        return ConvertUtils.convert(actorRepository.save(actor), ActorResponseDTO.class);
    }

}
