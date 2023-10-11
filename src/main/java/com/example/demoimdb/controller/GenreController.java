package com.example.demoimdb.controller;

import com.example.demoimdb.dto.genre.GenreRequestDTO;
import com.example.demoimdb.dto.genre.GenreResponseDTO;
import com.example.demoimdb.dto.genre.ListGenresResponseDTO;
import com.example.demoimdb.dto.genre.SearchGenreRequestDTO;
import com.example.demoimdb.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/genre")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @PostMapping("/search")
    public ResponseEntity<ListGenresResponseDTO> searchGenre(@RequestBody SearchGenreRequestDTO searchGenreRequestDTO) {
        return ResponseEntity.ok(genreService.searchGenre(searchGenreRequestDTO));
    }

    @PostMapping("")
    public ResponseEntity<GenreResponseDTO> addGenre(@Valid  @RequestBody GenreRequestDTO genreRequestDTO) throws IOException {
        return ResponseEntity.ok(genreService.addGenre(genreRequestDTO));
    }

    @PutMapping("")
    public ResponseEntity<GenreResponseDTO> editGenre(@Valid  @RequestBody GenreRequestDTO genreRequestDTO) throws IOException {
        return ResponseEntity.ok(genreService.editGenre(genreRequestDTO));
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteGenre(@Valid  @RequestBody GenreRequestDTO genreRequestDTO) throws IOException {
        return ResponseEntity.ok(genreService.deleteGenre(genreRequestDTO));
    }
}
