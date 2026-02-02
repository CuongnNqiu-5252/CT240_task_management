package com.pro.task_management.service;


import com.pro.task_management.dto.request.ProjectMemberRequestDTO;
import com.pro.task_management.dto.response.ProjectMemberResponseDTO;

import java.util.List;

public interface ProjectMemberService {

    ProjectMemberResponseDTO addProjectMember(ProjectMemberRequestDTO requestDTO);

    List<ProjectMemberResponseDTO> getProjectMembers(String projectId);

    List<ProjectMemberResponseDTO> getUserProjects(String userId);

    void removeProjectMember(String userId, String projectId);
}
