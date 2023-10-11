package com.example.demoimdb.service;

import com.example.demoimdb.dto.account.BaseAccountDTO;
import com.example.demoimdb.dto.actor.ActorRequestDTO;
import com.example.demoimdb.dto.actor.ActorResponseDTO;
import com.example.demoimdb.dto.actor.ListActorsResponseDTO;
import com.example.demoimdb.dto.actor.SearchActorRequestDTO;
import com.example.demoimdb.exception.ApiInputException;
import com.example.demoimdb.model.Actor;
import com.example.demoimdb.repository.ActorRepository;
import com.example.demoimdb.utils.ConvertUtils;
import com.example.demoimdb.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private AccountService accountService;

    public ActorResponseDTO addActor(ActorRequestDTO actorRequestDTO) throws IOException {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(actorRequestDTO.getUsername(), actorRequestDTO.getPassword());
        accountService.checkAdmin(baseAccountDTO);
        Actor actor = new Actor(null, actorRequestDTO.getName(), actorRequestDTO.getDescription(), actorRequestDTO.getImage(),
                actorRequestDTO.getDob(), null, null);
        return ConvertUtils.convert(actorRepository.save(actor), ActorResponseDTO.class);
    }

    public ActorResponseDTO editActor(ActorRequestDTO actorRequestDTO) throws IOException {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(actorRequestDTO.getUsername(), actorRequestDTO.getPassword());
        accountService.checkAdmin(baseAccountDTO);
        try {
            Actor actor = actorRepository.findById(actorRequestDTO.getId()).get();
            actor.setName(actorRequestDTO.getName());
            actor.setDescription(actorRequestDTO.getDescription());
            actor.setImage(actorRequestDTO.getImage());
            actor.setDob(actorRequestDTO.getDob());
            return ConvertUtils.convert(actorRepository.save(actor), ActorResponseDTO.class);
        } catch (Exception e) {
            throw new ApiInputException("ID không hợp lệ!");
        }
    }

    public String deleteActor(ActorRequestDTO actorRequestDTO) {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(actorRequestDTO.getUsername(), actorRequestDTO.getPassword());
        accountService.checkAdmin(baseAccountDTO);
        try {
            actorRepository.deleteById(actorRequestDTO.getId());
            return "Xoá thành công!";
        } catch (Exception e) {
            throw new ApiInputException("Xoá thất bại!");
        }
    }

    public ListActorsResponseDTO searchActor(SearchActorRequestDTO searchActorRequestDTO) {
        BaseAccountDTO baseAccountDTO = new BaseAccountDTO(searchActorRequestDTO.getUsername(), searchActorRequestDTO.getPassword());
        accountService.checkAdmin(baseAccountDTO);
        searchActorRequestDTO.validateInput();
        Pageable pageable;
        if ("ASC".equals(searchActorRequestDTO.getOrderBy())) {
            pageable = PageRequest.of(searchActorRequestDTO.getPageIndex() - 1, searchActorRequestDTO.getPageSize(), Sort.by(searchActorRequestDTO.getSortBy()).ascending());
        } else {
            pageable = PageRequest.of(searchActorRequestDTO.getPageIndex() - 1, searchActorRequestDTO.getPageSize(), Sort.by(searchActorRequestDTO.getSortBy()).descending());
        }
        Page<Actor> pageActors = actorRepository.searchActor(searchActorRequestDTO.getName(), pageable);
        List<Actor> listActors = pageActors.toList();
        List<ActorResponseDTO> listActorsDto = ConvertUtils.convertList(listActors, ActorResponseDTO.class);
        ListActorsResponseDTO listActorsResponseDTO = new ListActorsResponseDTO();
        listActorsResponseDTO.setListActors(listActorsDto);
        listActorsResponseDTO.setPageIndex(searchActorRequestDTO.getPageIndex());
        listActorsResponseDTO.setPageSize(searchActorRequestDTO.getPageSize());
        listActorsResponseDTO.setTotalRecords(pageActors.getTotalElements());
        return listActorsResponseDTO;
    }

}
