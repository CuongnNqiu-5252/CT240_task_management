package com.pro.task_management.service;

import com.pro.task_management.dto.request.ProjectRequestDTO;
import com.pro.task_management.dto.response.ProjectResponseDTO;
import com.pro.task_management.entity.Project;
import com.pro.task_management.enums.ProjectStatus;
import com.pro.task_management.exception.ResourceNotFoundException;
import com.pro.task_management.mapper.ProjectMapper;
import com.pro.task_management.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectResponseDTO createProject(ProjectRequestDTO requestDTO) {
        Project project = projectMapper.toEntity(requestDTO);
        if (project.getStatus() == null) {
            project.setStatus(ProjectStatus.ACTIVE);
        }
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDTO(savedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponseDTO getProjectById(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        return projectMapper.toDTO(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projectMapper.toDTOList(projects);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByStatus(ProjectStatus status) {
        List<Project> projects = projectRepository.findByStatus(status);
        return projectMapper.toDTOList(projects);
    }

    @Override
    public ProjectResponseDTO updateProject(String id, ProjectRequestDTO requestDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        projectMapper.updateEntityFromDTO(requestDTO, project);
        Project updatedProject = projectRepository.save(project);
        return projectMapper.toDTO(updatedProject);
    }

    @Override
    public void deleteProject(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        projectRepository.delete(project);
    }
}
