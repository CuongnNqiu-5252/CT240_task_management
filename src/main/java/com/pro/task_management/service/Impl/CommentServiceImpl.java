package com.pro.task_management.service.Impl;

import com.pro.task_management.dto.request.CommentRequestDTO;
import com.pro.task_management.dto.request.CommentUpdateRequestDTO;
import com.pro.task_management.dto.response.CommentResponseDTO;
import com.pro.task_management.entity.Comment;
import com.pro.task_management.entity.Task;
import com.pro.task_management.entity.User;
import com.pro.task_management.exception.AppException;
import com.pro.task_management.mapper.CommentMapper;
import com.pro.task_management.repository.CommentRepository;
import com.pro.task_management.repository.TaskRepository;
import com.pro.task_management.repository.UserRepository;
import com.pro.task_management.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentResponseDTO createComment(CommentRequestDTO requestDTO) {
        Task task = taskRepository.findById(requestDTO.getTaskId()).orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Task Not found"));

        User user = userRepository.findById(requestDTO.getUserId()).orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "User Not found"));

        Comment comment = Comment.builder().content(requestDTO.getContent()).createdDate(LocalDateTime.now()).task(task).user(user).build();

        Comment saved = commentRepository.save(comment);
        return commentMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponseDTO getCommentById(String id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Not found"));
        return commentMapper.toDTO(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getCommentsByTask(String taskId) {
        List<Comment> comments = commentRepository.findByTaskId(taskId);
        return commentMapper.toDTOList(comments);
    }

    @Override
    @PreAuthorize("@permissionService.isCommentOwner(#id)")
    public CommentResponseDTO updateComment(String id, CommentUpdateRequestDTO requestDTO) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Comment Not found"));
        comment.setContent(requestDTO.getContent());
        Comment updated = commentRepository.save(comment);
        return commentMapper.toDTO(updated);
    }

    @Override
    @PreAuthorize("@permissionService.isCommentOwner(#id)")
    public CommentResponseDTO deleteComment(String id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Not found"));

        String taskId = comment.getTask().getId();
        commentRepository.delete(comment);

        CommentResponseDTO response = commentMapper.toDTO(comment);
        response.setTaskId(taskId); // Set taskId in the response for WebSocket notification
        return response;
    }
}
