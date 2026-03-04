package com.pro.task_management.controller;

import com.pro.task_management.dto.CommentEvent;
import com.pro.task_management.dto.request.CommentRequestDTO;
import com.pro.task_management.dto.request.CommentUpdateRequestDTO;
import com.pro.task_management.dto.response.ApiResponse;
import com.pro.task_management.dto.response.CommentResponseDTO;
import com.pro.task_management.enums.CommentEventType;
import com.pro.task_management.service.CommentService;
import com.pro.task_management.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final SimpMessagingTemplate messagingTemplate;

    private void sendCommentEvent(String taskId, CommentResponseDTO payload, CommentEventType type) {
        String destination = "/topic/task/" + taskId + "/comments";
        String currentUserId = SecurityUtils.getCurrentUserId();

        CommentEvent event = CommentEvent.builder().actorId(currentUserId).payload(payload).type(type).build();

        messagingTemplate.convertAndSend(destination, event);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDTO>> createComment(@Valid @RequestBody CommentRequestDTO requestDTO) {
        CommentResponseDTO response = commentService.createComment(requestDTO);

//      Websocket - Broadcast the new comment to all subscribers of the task's comment topic
        sendCommentEvent(response.getTaskId(), response, CommentEventType.COMMENT_CREATED);

        return new ResponseEntity<>(ApiResponse.<CommentResponseDTO>builder().data(response).message("Comment created successfully").build(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> getCommentById(@PathVariable String id) {
        CommentResponseDTO response = commentService.getCommentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<List<CommentResponseDTO>>> getCommentsByTask(@PathVariable String taskId) {
        List<CommentResponseDTO> response = commentService.getCommentsByTask(taskId);

        ApiResponse<List<CommentResponseDTO>> apiResponse = ApiResponse.<List<CommentResponseDTO>>builder().data(response).message("Comments retrieved successfully").build();

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentResponseDTO>> updateComment(@PathVariable String id, @Valid @RequestBody CommentUpdateRequestDTO requestDTO) {
        CommentResponseDTO response = commentService.updateComment(id, requestDTO);

//       Websocket - Broadcast the updated comment to all subscribers of the task's comment topic
        sendCommentEvent(response.getTaskId(), response, CommentEventType.COMMENT_UPDATED);

        return ResponseEntity.ok(ApiResponse.<CommentResponseDTO>builder().data(response).message("Comment updated successfully").build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentResponseDTO>> deleteComment(@PathVariable String id) {
        CommentResponseDTO response = commentService.deleteComment(id);

        sendCommentEvent(response.getTaskId(), response, CommentEventType.COMMENT_DELETED);

        return ResponseEntity.ok(ApiResponse.<CommentResponseDTO>builder().data(response).message("Comment deleted successfully").build());
    }
}
