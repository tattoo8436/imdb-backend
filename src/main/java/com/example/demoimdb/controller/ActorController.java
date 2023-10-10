package com.example.demoimdb.controller;

import com.example.demoimdb.dto.actor.*;
import com.example.demoimdb.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/actor")
public class ActorController {
    @Autowired
    private ActorService actorService;

    @PostMapping("/search")
    public ResponseEntity<ListActorsResponseDTO> searchActor(@RequestBody SearchActorRequestDTO searchActorRequestDTO) {
        return ResponseEntity.ok(actorService.searchActor(searchActorRequestDTO));
    }

    @PostMapping("")
    public ResponseEntity<ActorResponseDTO> addActor(@Valid  @RequestBody ActorRequestDTO actorRequestDTO) throws IOException {
        return ResponseEntity.ok(actorService.addActor(actorRequestDTO));
    }

    @PutMapping("")
    public ResponseEntity<ActorResponseDTO> editActor(@Valid  @RequestBody ActorRequestDTO actorRequestDTO) throws IOException {
        return ResponseEntity.ok(actorService.editActor(actorRequestDTO));
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteActor(@Valid  @RequestBody ActorRequestDTO actorRequestDTO) throws IOException {
        return ResponseEntity.ok(actorService.deleteActor(actorRequestDTO));
    }
}
