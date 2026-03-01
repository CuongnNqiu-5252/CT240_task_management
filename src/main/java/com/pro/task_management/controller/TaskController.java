package com.pro.task_management.controller;


import com.pro.task_management.dto.request.TaskRequestDTO;
import com.pro.task_management.dto.request.TaskUpdateDTO;
import com.pro.task_management.dto.response.TaskResponseDTO;
import com.pro.task_management.enums.TaskStatus;
import com.pro.task_management.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO requestDTO) {
        TaskResponseDTO response = taskService.createTask(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable String id) {
        TaskResponseDTO response = taskService.getTaskById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> response = taskService.getAllTasks();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByProject(@PathVariable String projectId) {
        List<TaskResponseDTO> response = taskService.getTasksByProject(projectId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByAssignee(@PathVariable String assigneeId) {
        List<TaskResponseDTO> response = taskService.getTasksByAssignee(assigneeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByStatus(@PathVariable TaskStatus status) {
        List<TaskResponseDTO> response = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable String id,
            @Valid @RequestBody TaskUpdateDTO requestDTO) {
        TaskResponseDTO response = taskService.updateTask(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
