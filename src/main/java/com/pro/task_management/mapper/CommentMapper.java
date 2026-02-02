package com.pro.task_management.mapper;

import com.pro.task_management.dto.response.CommentResponseDTO;
import com.pro.task_management.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "task.title", target = "taskTitle")
    CommentResponseDTO toDTO(Comment comment);

    List<CommentResponseDTO> toDTOList(List<Comment> comments);
}
