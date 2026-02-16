package com.pro.task_management.controller;

import com.pro.task_management.dto.request.ProjectRequestDTO;
import com.pro.task_management.dto.response.ApiResponse;
import com.pro.task_management.dto.response.ProjectResponseDTO;
import com.pro.task_management.enums.ProjectStatus;
import com.pro.task_management.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody ProjectRequestDTO requestDTO) {
        ProjectResponseDTO response = projectService.createProject(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable String id) {
        ProjectResponseDTO response = projectService.getProjectById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponseDTO>>> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        ApiResponse<List<ProjectResponseDTO>> response = projectService.getAllProjects(page, size);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProjectResponseDTO>> getProjectsByStatus(@PathVariable ProjectStatus status) {
        List<ProjectResponseDTO> response = projectService.getProjectsByStatus(status);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable String id,
            @Valid @RequestBody ProjectRequestDTO requestDTO) {
        ProjectResponseDTO response = projectService.updateProject(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
