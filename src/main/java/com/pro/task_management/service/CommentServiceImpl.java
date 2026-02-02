package com.pro.task_management.service;

import com.pro.task_management.dto.request.CommentRequestDTO;
import com.pro.task_management.dto.response.CommentResponseDTO;
import com.pro.task_management.entity.Comment;
import com.pro.task_management.entity.Task;
import com.pro.task_management.entity.User;
import com.pro.task_management.exception.ResourceNotFoundException;
import com.pro.task_management.mapper.CommentMapper;
import com.pro.task_management.repository.CommentRepository;
import com.pro.task_management.repository.TaskRepository;
import com.pro.task_management.repository.UserRepository;
import com.pro.task_management.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Task task = taskRepository.findById(requestDTO.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", requestDTO.getTaskId()));

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", requestDTO.getUserId()));

        Comment comment = Comment.builder()
                .content(requestDTO.getContent())
                .task(task)
                .user(user)
                .build();

        Comment saved = commentRepository.save(comment);
        return commentMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponseDTO getCommentById(String id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        return commentMapper.toDTO(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getCommentsByTask(String taskId) {
        List<Comment> comments = commentRepository.findByTaskId(taskId);
        return commentMapper.toDTOList(comments);
    }

    @Override
    public CommentResponseDTO updateComment(String id, CommentRequestDTO requestDTO) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        comment.setContent(requestDTO.getContent());
        Comment updated = commentRepository.save(comment);
        return commentMapper.toDTO(updated);
    }

    @Override
    public void deleteComment(String id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        commentRepository.delete(comment);
    }
}
