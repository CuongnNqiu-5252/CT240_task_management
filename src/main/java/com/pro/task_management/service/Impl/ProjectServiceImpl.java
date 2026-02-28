package com.pro.task_management.service.Impl;

import com.pro.task_management.dto.request.ProjectRequestDTO;
import com.pro.task_management.dto.response.PageResponse;
import com.pro.task_management.dto.response.Pagination;
import com.pro.task_management.dto.response.ProjectResponseDTO;
import com.pro.task_management.dto.response.ProjectResponseWithMembersDTO;
import com.pro.task_management.entity.Project;
import com.pro.task_management.entity.ProjectMember;
import com.pro.task_management.entity.User;
import com.pro.task_management.enums.ProjectRole;
import com.pro.task_management.enums.ProjectStatus;
import com.pro.task_management.exception.AppException;
import com.pro.task_management.mapper.BoardColumnMapper;
import com.pro.task_management.mapper.ProjectMapper;
import com.pro.task_management.mapper.ProjectMemberMapper;
import com.pro.task_management.repository.ProjectMemberRepository;
import com.pro.task_management.repository.ProjectRepository;
import com.pro.task_management.repository.UserRepository;
import com.pro.task_management.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final BoardColumnMapper boardColumnMapper;

    private static final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final ProjectMemberMapper projectMemberMapper;

    @Override
    public ProjectResponseDTO createProject(ProjectRequestDTO requestDTO) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(userId);
        User user = userRepository.findByUsername(userId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Not found"));
        Project project = projectMapper.toEntity(requestDTO);
        if (project.getStatus() == null) {
            project.setStatus(ProjectStatus.ACTIVE);
        }
        Project savedProject = projectRepository.save(project);

        ProjectMember projectMember =
                ProjectMember.create(user, savedProject, ProjectRole.MANAGER);

        projectMemberRepository.save(projectMember);
        return ProjectResponseDTO.builder()
                .id(savedProject.getId())
                .name(savedProject.getName())
                .description(savedProject.getDescription())
                .status(savedProject.getStatus())
                .projectMemberResponseDTO(projectMemberMapper.toDTO(projectMember))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponseDTO getProjectById(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Project not found"));


        return ProjectResponseDTO.builder()
                .id(project.getId())
                .status(project.getStatus())
                .name(project.getName())
                .owner(project.getProjectMembers().stream().filter(member -> {
                    return member.getRole().equals(ProjectRole.MANAGER);
                }).toList().getFirst().getUser().getUsername())
                .description(project.getDescription())
                .listBoardColumnResponseDTO(boardColumnMapper.toDTOList(project.getBoardColumns()))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<List<ProjectResponseWithMembersDTO>> getAllProjects(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Project> projectPage = projectRepository.findAllWithMembers(pageable);

        List<ProjectResponseWithMembersDTO> projectsDTO = projectPage.getContent().stream()
                .map(project -> {
                    return ProjectResponseWithMembersDTO.builder()
                            .owner(project.getProjectMembers().stream().filter(member -> {
                                return member.getRole().equals(ProjectRole.MANAGER);
                            }).toList().getFirst().getUser().getUsername())
                            .id(project.getId())
                            .name(project.getName())
                            .description(project.getDescription())
                            .status(project.getStatus())

                            .build();
                })
                .toList();

        Pagination pagination = Pagination.builder()
                .page(page)
                .size(size)
                .totalElements(projectPage.getTotalElements())
                .totalPages(projectPage.getTotalPages())
                .build();

        return PageResponse.<List<ProjectResponseWithMembersDTO>>builder()
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
