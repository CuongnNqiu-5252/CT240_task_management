package com.pro.task_management.service;



import com.pro.task_management.dto.request.ProjectRequestDTO;
import com.pro.task_management.dto.response.ProjectResponseDTO;
import com.pro.task_management.enums.ProjectStatus;

import java.util.List;

public interface ProjectService {

    ProjectResponseDTO createProject(ProjectRequestDTO requestDTO);

    ProjectResponseDTO getProjectById(String id);

    List<ProjectResponseDTO> getAllProjects();

    List<ProjectResponseDTO> getProjectsByStatus(ProjectStatus status);

    ProjectResponseDTO updateProject(String id, ProjectRequestDTO requestDTO);

    void deleteProject(String id);
}
