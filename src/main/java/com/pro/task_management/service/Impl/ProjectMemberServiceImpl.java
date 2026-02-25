package com.pro.task_management.service.Impl;

import com.pro.task_management.dto.request.ProjectMemberRequestDTO;
import com.pro.task_management.dto.response.PageResponse;
import com.pro.task_management.dto.response.Pagination;
import com.pro.task_management.dto.response.ProjectMemberResponseDTO;
import com.pro.task_management.entity.Project;
import com.pro.task_management.entity.ProjectMember;
import com.pro.task_management.entity.ProjectMemberId;
import com.pro.task_management.entity.User;
import com.pro.task_management.exception.AppException;
import com.pro.task_management.mapper.ProjectMemberMapper;
import com.pro.task_management.repository.ProjectMemberRepository;
import com.pro.task_management.repository.ProjectRepository;
import com.pro.task_management.repository.UserRepository;
import com.pro.task_management.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberMapper projectMemberMapper;

    @Override
    public ProjectMemberResponseDTO addProjectMember(ProjectMemberRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not Found"));

        Project project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));

        ProjectMember projectMember = ProjectMember.create(user, project, requestDTO.getRole());
        ProjectMember savedMember = projectMemberRepository.save(projectMember);
        return projectMemberMapper.toDTO(savedMember);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<List<ProjectMemberResponseDTO>> getProjectMembers(String projectId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ProjectMember> projectMemberPage = projectMemberRepository.findAll(pageable);

        List<ProjectMemberResponseDTO> projectMemberDTOPage = projectMemberPage.getContent().stream().map(projectMemberMapper::toDTO).toList();

        Pagination pagination = Pagination.builder()
                .page(page)
                .size(size)
                .totalPages(projectMemberPage.getTotalPages())
                .totalElements(projectMemberPage.getTotalElements())
                .build();

        return PageResponse.<List<ProjectMemberResponseDTO>>builder()
                .data(projectMemberDTOPage)
                .pagination(pagination)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<List<ProjectMemberResponseDTO>> getUserProjects(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ProjectMember> projectMemberPage = projectMemberRepository.findByUserId(userId, pageable);

        List<ProjectMemberResponseDTO> userProjectDTOPage = projectMemberPage.getContent().stream().map(projectMemberMapper::toDTO).toList();

        Pagination pagination = Pagination.builder()
                .page(page)
                .size(size)
                .totalElements(projectMemberPage.getTotalElements())
                .totalPages(projectMemberPage.getTotalPages())
                .build();

        return PageResponse.<List<ProjectMemberResponseDTO>>builder()
                .data(userProjectDTOPage)
                .pagination(pagination)
                .build();
    }

    @Override
    public void removeProjectMember(String userId, String projectId) {
        ProjectMemberId id = new ProjectMemberId(userId, projectId);
        ProjectMember member = projectMemberRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
        projectMemberRepository.delete(member);
    }
}
