package com.pro.task_management.mapper;

import com.pro.task_management.dto.response.ProjectMemberResponseDTO;
import com.pro.task_management.entity.ProjectMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {

    ProjectMemberResponseDTO toDTO(ProjectMember projectMember);

    List<ProjectMemberResponseDTO> toDTOList(List<ProjectMember> projectMembers);
}
