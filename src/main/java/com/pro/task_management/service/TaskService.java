package com.pro.task_management.service;

import com.pro.task_management.dto.request.TaskRequestDTO;
import com.pro.task_management.dto.request.TaskUpdateDTO;
import com.pro.task_management.dto.response.TaskResponseDTO;
import com.pro.task_management.enums.TaskStatus;

import java.util.List;

public interface TaskService {

    TaskResponseDTO createTask(TaskRequestDTO requestDTO);

    TaskResponseDTO getTaskById(String id);

    List<TaskResponseDTO> getAllTasks();

    List<TaskResponseDTO> getTasksByProject(String projectId);

    List<TaskResponseDTO> getTasksByAssignee(String assigneeId);

    List<TaskResponseDTO> getTasksByStatus(TaskStatus status);

    TaskResponseDTO updateTask(String id, TaskUpdateDTO requestDTO);

    void deleteTask(String id);
}
