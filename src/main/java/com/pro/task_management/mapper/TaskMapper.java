package com.pro.task_management.mapper;

import com.pro.task_management.dto.response.TaskResponseDTO;
import com.pro.task_management.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    @Mapping(source = "assignee.id", target = "assigneeId")
    @Mapping(source = "assignee.username", target = "assigneeName")
    @Mapping(source = "creator.id", target = "creatorId")
    @Mapping(source = "creator.username", target = "creatorName")
    TaskResponseDTO toDTO(Task task);

    List<TaskResponseDTO> toDTOList(List<Task> tasks);
}
