package com.pro.task_management.service.Impl;

import com.pro.task_management.dto.request.ProjectRequestDTO;
import com.pro.task_management.dto.response.PageResponse;
import com.pro.task_management.dto.response.Pagination;
import com.pro.task_management.dto.response.ProjectResponseDTO;
import com.pro.task_management.entity.Project;
import com.pro.task_management.entity.ProjectMember;
import com.pro.task_management.entity.User;
import com.pro.task_management.enums.ProjectRole;
import com.pro.task_management.enums.ProjectStatus;
import com.pro.task_management.exception.AppException;
import com.pro.task_management.mapper.ProjectMapper;
import com.pro.task_management.repository.ProjectMemberRepository;
import com.pro.task_management.repository.ProjectRepository;
import com.pro.task_management.repository.UserRepository;
import com.pro.task_management.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;

    @Override
    public ProjectResponseDTO createProject(ProjectRequestDTO requestDTO) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Not found"));
        Project project = projectMapper.toEntity(requestDTO);
        if (project.getStatus() == null) {
            project.setStatus(ProjectStatus.ACTIVE);
        }
        ProjectMember projectMember = ProjectMember.create(user,project, ProjectRole.MANAGER);
        projectMemberRepository.save(projectMember);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDTO(savedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponseDTO getProjectById(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
        return projectMapper.toDTO(project);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<List<ProjectResponseDTO>> getAllProjects(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Project> projectPage = projectRepository.findAll(pageable);

        List<ProjectResponseDTO> projectsDTO = projectPage.getContent().stream()
                .map(projectMapper::toDTO)
                .toList();

        Pagination pagination = Pagination.builder()
                .page(page)
                .size(size)
                .totalElements(projectPage.getTotalElements())
                .totalPages(projectPage.getTotalPages())
                .build();

        return PageResponse.<List<ProjectResponseDTO>>builder()
                .data(projectsDTO)
                .pagination(pagination)
                .build();
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
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
        projectMapper.updateEntityFromDTO(requestDTO, project);
        Project updatedProject = projectRepository.save(project);
        return projectMapper.toDTO(updatedProject);
    }

    @Override
    public void deleteProject(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
        projectRepository.delete(project);
    }
}
