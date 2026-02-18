package com.pro.task_management.controller;

import com.pro.task_management.dto.request.ProjectRequestDTO;
import com.pro.task_management.dto.response.ApiResponse;
import com.pro.task_management.dto.response.PageResponse;
import com.pro.task_management.dto.response.ProjectResponseDTO;
import com.pro.task_management.enums.ProjectStatus;
import com.pro.task_management.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponseDTO>> createProject(@Valid @RequestBody ProjectRequestDTO requestDTO) {
        ProjectResponseDTO response = projectService.createProject(requestDTO);
        return new ResponseEntity<ApiResponse<ProjectResponseDTO>>(ApiResponse.<ProjectResponseDTO>builder().data(response).message("Project created successfully").build(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponseDTO>> getProjectById(@PathVariable String id) {
        ProjectResponseDTO response = projectService.getProjectById(id);
        return ResponseEntity.ok(ApiResponse.<ProjectResponseDTO>builder()
                .data(response)
                .message("Project retrieved successfully")
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponseDTO>>> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<List<ProjectResponseDTO>> serviceResponse = projectService.getAllProjects(page, size);


        return ResponseEntity.ok(ApiResponse.<List<ProjectResponseDTO>>builder().data(serviceResponse.getData()).pagination(serviceResponse.getPagination()).message("Projects retrieved successfully").build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<ProjectResponseDTO>>> getProjectsByStatus(@PathVariable ProjectStatus status) {
        List<ProjectResponseDTO> response = projectService.getProjectsByStatus(status);
        return ResponseEntity.ok(ApiResponse.<List<ProjectResponseDTO>>builder()
                .data(response).message("Projects retrieved successfully")
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponseDTO>> updateProject(@PathVariable String id, @Valid @RequestBody ProjectRequestDTO requestDTO) {
        ProjectResponseDTO response = projectService.updateProject(id, requestDTO);
        return ResponseEntity.ok(ApiResponse.<ProjectResponseDTO>builder()
                .data(response)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponseDTO>> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponse.<ProjectResponseDTO>builder()
                .data(null)
                .message("Project deleted successfully")
                .build());
    }
}
