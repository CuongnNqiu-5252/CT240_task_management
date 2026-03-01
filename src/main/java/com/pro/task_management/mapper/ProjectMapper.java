package com.pro.task_management.mapper;

import com.pro.task_management.dto.request.ProjectRequestDTO;
import com.pro.task_management.dto.request.ProjectUpdateDTO;
import com.pro.task_management.dto.response.ProjectResponseDTO;
import com.pro.task_management.dto.response.ProjectResponseWithMembersDTO;
import com.pro.task_management.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper {

    ProjectResponseDTO toDTO(Project project);
    ProjectResponseWithMembersDTO toDTOWithMembers(Project project);

    List<ProjectResponseDTO> toDTOList(List<Project> projects);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "boardColumns", ignore = true)
    @Mapping(target = "projectMembers", ignore = true)
    Project toEntity(ProjectRequestDTO dto);



    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "boardColumns", ignore = true)
    @Mapping(target = "projectMembers", ignore = true)
    void updateEntityFromDTO(ProjectUpdateDTO dto, @MappingTarget Project project);
}
