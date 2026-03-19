package com.pro.task_management.controller;

import com.pro.task_management.dto.request.ProjectMemberRequestDTO;
import com.pro.task_management.dto.response.ApiResponse;
import com.pro.task_management.dto.response.PageResponse;
import com.pro.task_management.dto.response.ProjectMemberResponseDTO;
import com.pro.task_management.dto.response.ProjectResponseDTO;
import com.pro.task_management.service.ProjectMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project-members")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    // Dùng PreAuthorize để kiểm tra nếu người dùng hiện tại là MANAGER HOẶC OWNER của dự án thì mới được phép thêm thành viên vào dự án
    @PreAuthorize("@permissionService.isManager(#requestDTO.projectId) || @permissionService.isOwner(#requestDTO.projectId)")
    @PostMapping
    public ResponseEntity<ApiResponse<ProjectMemberResponseDTO>> addProjectMember(@Valid @RequestBody ProjectMemberRequestDTO requestDTO) {
        ProjectMemberResponseDTO response = projectMemberService.addProjectMember(requestDTO);
        return ResponseEntity.ok(ApiResponse.<ProjectMemberResponseDTO>builder().data(response).message("Project member added successfully").build());
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<ProjectMemberResponseDTO>>> getProjectMembers(
            @PathVariable String projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<List<ProjectMemberResponseDTO>> response = projectMemberService.getProjectMembers(projectId, page, size);
        return ResponseEntity.ok(ApiResponse.<List<ProjectMemberResponseDTO>>builder()
                .data(response.getData())
                .pagination(response.getPagination())
                .message("Project members retrieved successfully")
                .build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ProjectMemberResponseDTO>>> getUserProjects(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<List<ProjectMemberResponseDTO>> response = projectMemberService.getUserProjects(userId, page, size);
        return ResponseEntity.ok(ApiResponse.<List<ProjectMemberResponseDTO>>builder()
                .data(response.getData())
                .pagination(response.getPagination())
                .message("User's projects retrieved successfully")
                .build());
    }

    @PreAuthorize("@permissionService.isManager(#projectId) || @permissionService.isOwner(#projectId)")
    @DeleteMapping("/user/{userId}/project/{projectId}")
    public ResponseEntity<ApiResponse<ProjectResponseDTO>> removeProjectMember(@PathVariable String userId, @PathVariable String projectId) {
        projectMemberService.removeProjectMember(userId, projectId);
        return ResponseEntity.ok(ApiResponse.<ProjectResponseDTO>builder().data(null).message("Project member removed successfully").build());
    }
}
