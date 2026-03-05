package com.pro.task_management.mapper;

import com.pro.task_management.dto.response.TaskAssigneesResponseDTO;
import com.pro.task_management.entity.TaskAssignees;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskAssigneesMapper {

    TaskAssigneesResponseDTO toDTO(TaskAssignees taskAssignees);

    List<TaskAssigneesResponseDTO> toListDTO(List<TaskAssignees> taskAssigneesList);
}
