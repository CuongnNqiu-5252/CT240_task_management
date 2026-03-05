package com.pro.task_management.controller;

import com.pro.task_management.dto.request.TaskAssigneesRequestDTO;
import com.pro.task_management.dto.response.*;
import com.pro.task_management.service.TaskAssigneesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-assignees")
@RequiredArgsConstructor
public class TaskAssigneesController {

    private final TaskAssigneesService taskAssigneesService;


    @PreAuthorize("@permissionService.isManager(#requestDTO.projectId)")
    @PostMapping
    public ResponseEntity<ApiResponse<TaskAssigneesResponseDTO>> addAssigneeToTask(@Valid @RequestBody TaskAssigneesRequestDTO requestDTO) {
        TaskAssigneesResponseDTO response = taskAssigneesService.addAssigneeToTask(requestDTO);
        return ResponseEntity.ok(ApiResponse.<TaskAssigneesResponseDTO>builder().data(response).message("Assignee has been added to task successfully").build());
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<List<TaskAssigneesResponseDTO>>> getAssigneesOfTask(
            @PathVariable String taskId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<List<TaskAssigneesResponseDTO>> response = taskAssigneesService.getAssigneesOfTask(taskId, page, size);
        return ResponseEntity.ok(ApiResponse.<List<TaskAssigneesResponseDTO>>builder()
                .data(response.getData())
                .pagination(response.getPagination())
                .message("Task assignee retrieved successfully")
                .build());
    }

    @PreAuthorize("@permissionService.isManager(#requestDTO.projectId)")
    @DeleteMapping
    public ResponseEntity<ApiResponse<TaskResponseDTO>> removeAssigneeOfTask(@Valid @RequestBody TaskAssigneesRequestDTO requestDTO) {
        taskAssigneesService.removeAssigneeOfTask(requestDTO.getUserId(), requestDTO.getTaskId());
        return ResponseEntity.ok(ApiResponse.<TaskResponseDTO>builder().data(null).message("Assignee has been removed from task successfully").build());
    }
}
