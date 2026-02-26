package com.pro.task_management.controller;

import com.pro.task_management.dto.request.CommentRequestDTO;
import com.pro.task_management.dto.response.CommentResponseDTO;
import com.pro.task_management.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDTO> createComment(@Valid @RequestBody CommentRequestDTO requestDTO) {
        CommentResponseDTO response = commentService.createComment(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> getCommentById(@PathVariable String id) {
        CommentResponseDTO response = commentService.getCommentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByTask(@PathVariable String taskId) {
        List<CommentResponseDTO> response = commentService.getCommentsByTask(taskId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(
            @PathVariable String id,
            @Valid @RequestBody CommentRequestDTO requestDTO) {
        CommentResponseDTO response = commentService.updateComment(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
