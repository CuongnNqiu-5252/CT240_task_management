package com.pro.task_management.service;

import com.pro.task_management.dto.request.CommentRequestDTO;
import com.pro.task_management.dto.request.CommentUpdateRequestDTO;
import com.pro.task_management.dto.response.CommentResponseDTO;

import java.util.List;

public interface CommentService {

    CommentResponseDTO createComment(CommentRequestDTO requestDTO);

    CommentResponseDTO getCommentById(String id);

    List<CommentResponseDTO> getCommentsByTask(String taskId);

    CommentResponseDTO updateComment(String id, CommentUpdateRequestDTO requestDTO);

    CommentResponseDTO deleteComment(String id);
}
