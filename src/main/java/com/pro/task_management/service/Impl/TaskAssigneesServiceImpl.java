package com.pro.task_management.service.Impl;

import com.pro.task_management.dto.request.TaskAssigneesRequestDTO;
import com.pro.task_management.dto.response.PageResponse;
import com.pro.task_management.dto.response.Pagination;
import com.pro.task_management.dto.response.TaskAssigneesResponseDTO;
import com.pro.task_management.entity.*;
import com.pro.task_management.exception.AppException;
import com.pro.task_management.mapper.TaskAssigneesMapper;
import com.pro.task_management.repository.*;
import com.pro.task_management.service.TaskAssigneesService;
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
public class TaskAssigneesServiceImpl implements TaskAssigneesService {

    private final TaskAssigneesRepository taskAssigneesRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskAssigneesMapper taskAssigneesMapper;

    @Override
    public TaskAssigneesResponseDTO addAssigneeToTask(TaskAssigneesRequestDTO requestDTO) {

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not Found"));

        Task task = taskRepository.findById(requestDTO.getTaskId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));

        TaskAssignees taskAssignees = TaskAssignees.create(user, task);
        TaskAssignees savedTaskAssignee = taskAssigneesRepository.save(taskAssignees);
        return taskAssigneesMapper.toDTO(savedTaskAssignee);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<List<TaskAssigneesResponseDTO>> getAssigneesOfTask(String taskId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<TaskAssignees> taskAssigneesPage = taskAssigneesRepository.findAll(pageable);

        List<TaskAssigneesResponseDTO> taskAssigneesDTOPage = taskAssigneesMapper.toListDTO(taskAssigneesPage.getContent());

        Pagination pagination = Pagination.builder()
                .page(page)
                .size(size)
                .totalPages(taskAssigneesPage.getTotalPages())
                .totalElements(taskAssigneesPage.getTotalElements())
                .build();

        return PageResponse.<List<TaskAssigneesResponseDTO>>builder()
                .data(taskAssigneesDTOPage)
                .pagination(pagination)
                .build();
    }

    @Override
    public void removeAssigneeOfTask(String userId, String taskId) {
        TaskAssigneesId id = new TaskAssigneesId(userId, taskId);
        TaskAssignees taskAssignees = taskAssigneesRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
        taskAssigneesRepository.delete(taskAssignees);
    }
}
