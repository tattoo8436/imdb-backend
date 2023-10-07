package com.example.demoimdb.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class FileController {
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
}
