package com.example.demoimdb.controller;

import com.example.demoimdb.dto.account.BaseAccountDTO;
import com.example.demoimdb.dto.actor.ActorRequestDTO;
import com.example.demoimdb.dto.actor.ActorResponseDTO;
import com.example.demoimdb.dto.actor.BaseActorDTO;
import com.example.demoimdb.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/actor")
public class ActorController {
    @Autowired
    private ActorService actorService;

    @GetMapping("")
    public ResponseEntity<List<ActorResponseDTO>> getAllActors() {
        return ResponseEntity.ok(actorService.getAllActors());
    }

    @PostMapping("")
    public ResponseEntity<ActorResponseDTO> saveActor(@RequestParam String username, @RequestParam String password, @RequestParam String name,
                                                      @RequestParam MultipartFile image) throws IOException {
        ActorRequestDTO actorRequestDTO = new ActorRequestDTO(new BaseAccountDTO(username, password), name, null, null, image);
        return ResponseEntity.ok(actorService.saveActor(actorRequestDTO));
    }
}
