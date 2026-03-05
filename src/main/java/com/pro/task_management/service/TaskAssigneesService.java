package com.pro.task_management.service;


import com.pro.task_management.dto.request.TaskAssigneesRequestDTO;
import com.pro.task_management.dto.response.PageResponse;
import com.pro.task_management.dto.response.TaskAssigneesResponseDTO;

import java.util.List;

public interface TaskAssigneesService {

    TaskAssigneesResponseDTO addAssigneeToTask(TaskAssigneesRequestDTO requestDTO);

    PageResponse<List<TaskAssigneesResponseDTO>> getAssigneesOfTask(String taskId, int page, int size);

    void removeAssigneeOfTask(String userId, String taskId);
}
