package com.example.demoimdb.controller;

import com.example.demoimdb.dto.file.ImageRequestDTO;
import com.example.demoimdb.exception.ApiInputException;
import com.example.demoimdb.service.FileService;
import com.example.demoimdb.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class FileController {
    @Autowired
    private FileService fileService;
    @GetMapping("/image/{name}")
    public ResponseEntity<Resource> getImage(@PathVariable String name) {
        try {
            String uploadDir = "upload/images";
            Path imagePath = Paths.get(uploadDir).resolve(name).normalize();
            if (Files.exists(imagePath) && imagePath.toFile().isFile()) {
                Resource resource = new UrlResource(imagePath.toUri());
                if (resource.exists()) {
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                            .body(resource);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/image/upload")
    public ResponseEntity<String> uploadImage(@Valid @ModelAttribute ImageRequestDTO imageRequestDTO){
        return ResponseEntity.ok(fileService.uploadImage(imageRequestDTO));
    }
}
