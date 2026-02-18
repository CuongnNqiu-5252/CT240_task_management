package com.pro.task_management.service;


import com.pro.task_management.dto.request.ProjectRequestDTO;
import com.pro.task_management.dto.response.ApiResponse;
import com.pro.task_management.dto.response.PageResponse;
import com.pro.task_management.dto.response.ProjectResponseDTO;
import com.pro.task_management.enums.ProjectStatus;

import java.util.List;

public interface ProjectService {

    ProjectResponseDTO createProject(ProjectRequestDTO requestDTO);

    ProjectResponseDTO getProjectById(String id);

    PageResponse<List<ProjectResponseDTO>> getAllProjects(int page, int size);

    List<ProjectResponseDTO> getProjectsByStatus(ProjectStatus status);

    ProjectResponseDTO updateProject(String id, ProjectRequestDTO requestDTO);

    void deleteProject(String id);
}
