package com.example.demoimdb.controller;

import com.example.demoimdb.dto.actor.ActorRequestDTO;
import com.example.demoimdb.dto.actor.ListActorsResponseDTO;
import com.example.demoimdb.dto.actor.SearchActorRequestDTO;
import com.example.demoimdb.dto.comment.CommentRequestDTO;
import com.example.demoimdb.model.Actor;
import com.example.demoimdb.model.Comment;
import com.example.demoimdb.service.ActorService;
import com.example.demoimdb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("")
    public ResponseEntity<Comment> addComment(@Valid @RequestBody CommentRequestDTO commentRequestDTO) {
        return ResponseEntity.ok(commentService.addComment(commentRequestDTO));
    }
}
