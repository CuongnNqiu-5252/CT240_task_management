package com.pro.task_management.controller;

import com.pro.task_management.dto.request.ProjectMemberRequestDTO;
import com.pro.task_management.dto.response.ProjectMemberResponseDTO;
import com.pro.task_management.service.ProjectMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project-members")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @PostMapping
    public ResponseEntity<ProjectMemberResponseDTO> addProjectMember(@Valid @RequestBody ProjectMemberRequestDTO requestDTO) {
        ProjectMemberResponseDTO response = projectMemberService.addProjectMember(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ProjectMemberResponseDTO>> getProjectMembers(@PathVariable String projectId) {
        List<ProjectMemberResponseDTO> response = projectMemberService.getProjectMembers(projectId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectMemberResponseDTO>> getUserProjects(@PathVariable String userId) {
        List<ProjectMemberResponseDTO> response = projectMemberService.getUserProjects(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user/{userId}/project/{projectId}")
    public ResponseEntity<Void> removeProjectMember(
            @PathVariable String userId,
            @PathVariable String projectId) {
        projectMemberService.removeProjectMember(userId, projectId);
        return ResponseEntity.noContent().build();
    }
}
