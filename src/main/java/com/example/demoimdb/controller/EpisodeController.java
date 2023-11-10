package com.example.demoimdb.controller;

import com.example.demoimdb.dto.episode.EpisodeRequestDTO;
import com.example.demoimdb.model.Episode;
import com.example.demoimdb.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/episode")
public class EpisodeController {
    @Autowired
    private EpisodeService episodeService;

    @GetMapping("")
    public  ResponseEntity<Episode> getEpisodeById(@RequestParam Long id){
        return ResponseEntity.ok(episodeService.getEpisodeById(id));
    }

    @PostMapping("")
    public ResponseEntity<Episode> addEpisode(@Valid @RequestBody EpisodeRequestDTO episodeRequestDTO) {
        return ResponseEntity.ok(episodeService.addEpisode(episodeRequestDTO));
    }

    @PutMapping("")
    public ResponseEntity<Episode> editEpisode(@Valid @RequestBody EpisodeRequestDTO episodeRequestDTO) {
        return ResponseEntity.ok(episodeService.editEpisode(episodeRequestDTO));
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteEpisode(@Valid @RequestBody EpisodeRequestDTO episodeRequestDTO) {
        return ResponseEntity.ok(episodeService.deleteEpisode(episodeRequestDTO));
    }
}
