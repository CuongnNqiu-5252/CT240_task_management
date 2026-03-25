package com.pro.task_management.mapper;

import com.pro.task_management.dto.request.BoardColumnUpdateDTO;
import com.pro.task_management.dto.request.TaskUpdateDTO;
import com.pro.task_management.dto.response.TaskAssigneesResponseDTO;
import com.pro.task_management.dto.response.TaskResponseDTO;
import com.pro.task_management.entity.BoardColumn;
import com.pro.task_management.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {TaskAssigneesMapper.class})
public interface TaskMapper {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    @Mapping(source = "creator.id", target = "creatorId")
    @Mapping(source = "creator.username", target = "creatorName")
    @Mapping(source = "column.id", target = "boardColumnId")
    @Mapping(source = "taskAssignees", target = "taskAssignees")
    TaskResponseDTO toDTO(Task task);

    List<TaskResponseDTO> toDTOList(List<Task> tasks);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(TaskUpdateDTO dto, @MappingTarget Task task);
}
