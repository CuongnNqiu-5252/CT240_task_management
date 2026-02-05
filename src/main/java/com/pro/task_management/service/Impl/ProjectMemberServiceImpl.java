package com.pro.task_management.service.Impl;

import com.pro.task_management.dto.request.ProjectMemberRequestDTO;
import com.pro.task_management.dto.response.ProjectMemberResponseDTO;
import com.pro.task_management.entity.Project;
import com.pro.task_management.entity.ProjectMember;
import com.pro.task_management.entity.ProjectMemberId;
import com.pro.task_management.entity.User;
import com.pro.task_management.exception.ResourceNotFoundException;
import com.pro.task_management.mapper.ProjectMemberMapper;
import com.pro.task_management.repository.ProjectMemberRepository;
import com.pro.task_management.repository.ProjectRepository;
import com.pro.task_management.repository.UserRepository;
import com.pro.task_management.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", requestDTO.getUserId()));

        Project project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", requestDTO.getProjectId()));

        ProjectMember projectMember = ProjectMember.create(user, project, requestDTO.getRole());
        ProjectMember savedMember = projectMemberRepository.save(projectMember);
        return projectMemberMapper.toDTO(savedMember);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectMemberResponseDTO> getProjectMembers(String projectId) {
        List<ProjectMember> members = projectMemberRepository.findByProjectId(projectId);
        return projectMemberMapper.toDTOList(members);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectMemberResponseDTO> getUserProjects(String userId) {
        List<ProjectMember> members = projectMemberRepository.findByUserId(userId);
        return projectMemberMapper.toDTOList(members);
    }

    @Override
    public void removeProjectMember(String userId, String projectId) {
        ProjectMemberId id = new ProjectMemberId(userId, projectId);
        ProjectMember member = projectMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ProjectMember not found with userId: " + userId + " and projectId: " + projectId));
        projectMemberRepository.delete(member);
    }
}
