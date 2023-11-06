package com.example.demoimdb.controller;

import com.example.demoimdb.dto.actor.*;
import com.example.demoimdb.model.Actor;
import com.example.demoimdb.model.Movie;
import com.example.demoimdb.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/actor")
public class ActorController {
    @Autowired
    private ActorService actorService;
    @GetMapping("")
    public ResponseEntity<Actor> getActorById(@RequestParam Long id) {
        return ResponseEntity.ok(actorService.getActorById(id));
    }

    @PostMapping("/search")
    public ResponseEntity<ListActorsResponseDTO> searchActor(@RequestBody SearchActorRequestDTO searchActorRequestDTO) {
        return ResponseEntity.ok(actorService.searchActor(searchActorRequestDTO));
    }

    @PostMapping("")
    public ResponseEntity<Actor> addActor(@Valid  @RequestBody ActorRequestDTO actorRequestDTO) {
        return ResponseEntity.ok(actorService.addActor(actorRequestDTO));
    }

    @PutMapping("")
    public ResponseEntity<Actor> editActor(@Valid  @RequestBody ActorRequestDTO actorRequestDTO) {
        return ResponseEntity.ok(actorService.editActor(actorRequestDTO));
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteActor(@Valid  @RequestBody ActorRequestDTO actorRequestDTO) {
        return ResponseEntity.ok(actorService.deleteActor(actorRequestDTO));
    }

    @GetMapping("/movie")
    public ResponseEntity<List<Movie>> getListMoviesByActor(@RequestParam Long actorId) {
        return ResponseEntity.ok(actorService.getListMoviesByActor(actorId));
    }
}
