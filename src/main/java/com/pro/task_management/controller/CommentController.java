package com.pro.task_management.controller;

import com.pro.task_management.dto.CommentEvent;
import com.pro.task_management.dto.request.CommentRequestDTO;
import com.pro.task_management.dto.response.ApiResponse;
import com.pro.task_management.dto.response.CommentResponseDTO;
import com.pro.task_management.enums.CommentEventType;
import com.pro.task_management.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDTO>> createComment(@Valid @RequestBody CommentRequestDTO requestDTO) {
        CommentResponseDTO response = commentService.createComment(requestDTO);

        String destination = "/topic/task/" + requestDTO.getTaskId() + "/comments";

        CommentEvent event = CommentEvent.builder()
                .payload(response)
                .type(CommentEventType.COMMENT_CREATED)
                .build();

        messagingTemplate.convertAndSend(destination, event);

        return new ResponseEntity<>(ApiResponse.<CommentResponseDTO>builder()
                .data(response)
                .message("Comment created successfully")
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> getCommentById(@PathVariable String id) {
        CommentResponseDTO response = commentService.getCommentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<List<CommentResponseDTO>>> getCommentsByTask(@PathVariable String taskId) {
        List<CommentResponseDTO> response = commentService.getCommentsByTask(taskId);

        ApiResponse<List<CommentResponseDTO>> apiResponse = ApiResponse.<List<CommentResponseDTO>>builder()
                .data(response)
                .message("Comments retrieved successfully")
                .build();

        return ResponseEntity.ok(apiResponse);
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
