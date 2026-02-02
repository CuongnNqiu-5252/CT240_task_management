package com.pro.task_management.mapper;

import com.pro.task_management.dto.response.ProjectMemberResponseDTO;
import com.pro.task_management.entity.ProjectMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "project.name", target = "projectName")
    ProjectMemberResponseDTO toDTO(ProjectMember projectMember);

    List<ProjectMemberResponseDTO> toDTOList(List<ProjectMember> projectMembers);
}
