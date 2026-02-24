package com.pro.task_management.service;


import com.pro.task_management.dto.request.ProjectMemberRequestDTO;
import com.pro.task_management.dto.response.PageResponse;
import com.pro.task_management.dto.response.ProjectMemberResponseDTO;

import java.util.List;

public interface ProjectMemberService {

    ProjectMemberResponseDTO addProjectMember(ProjectMemberRequestDTO requestDTO);

    PageResponse<List<ProjectMemberResponseDTO>> getProjectMembers(String projectId, int page, int size);

    PageResponse<List<ProjectMemberResponseDTO>> getUserProjects(String userId, int page, int size);

    void removeProjectMember(String userId, String projectId);
}
