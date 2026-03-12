package com.pro.task_management.mapper;

import com.pro.task_management.dto.response.CommentResponseDTO;
import com.pro.task_management.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {

    @Mapping(source = "user", target = "user")
    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "task.title", target = "taskTitle")
    CommentResponseDTO toDTO(Comment comment);

    List<CommentResponseDTO> toDTOList(List<Comment> comments);
}
