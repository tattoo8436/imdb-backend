package com.example.demoimdb.controller;

import com.example.demoimdb.dto.director.DirectorRequestDTO;
import com.example.demoimdb.dto.director.ListDirectorsResponseDTO;
import com.example.demoimdb.dto.director.SearchDirectorRequestDTO;
import com.example.demoimdb.model.Director;
import com.example.demoimdb.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/director")
public class DirectorController {
    @Autowired
    private DirectorService directorService;

    @PostMapping("/search")
    public ResponseEntity<ListDirectorsResponseDTO> searchDirector(@RequestBody SearchDirectorRequestDTO searchDirectorRequestDTO) {
        return ResponseEntity.ok(directorService.searchDirector(searchDirectorRequestDTO));
    }

    @PostMapping("")
    public ResponseEntity<Director> addDirector(@Valid  @RequestBody DirectorRequestDTO directorRequestDTO) {
        return ResponseEntity.ok(directorService.addDirector(directorRequestDTO));
    }

    @PutMapping("")
    public ResponseEntity<Director> editDirector(@Valid  @RequestBody DirectorRequestDTO directorRequestDTO) {
        return ResponseEntity.ok(directorService.editDirector(directorRequestDTO));
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteDirector(@Valid  @RequestBody DirectorRequestDTO directorRequestDTO) {
        return ResponseEntity.ok(directorService.deleteDirector(directorRequestDTO));
    }
}
