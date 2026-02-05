package com.pro.task_management.service.Impl;

import com.pro.task_management.dto.request.TaskRequestDTO;
import com.pro.task_management.dto.response.TaskResponseDTO;
import com.pro.task_management.entity.Project;
import com.pro.task_management.entity.Task;
import com.pro.task_management.entity.User;
import com.pro.task_management.enums.TaskStatus;
import com.pro.task_management.exception.ResourceNotFoundException;
import com.pro.task_management.mapper.TaskMapper;
import com.pro.task_management.repository.ProjectRepository;
import com.pro.task_management.repository.TaskRepository;
import com.pro.task_management.repository.UserRepository;
import com.pro.task_management.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
        Project project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", requestDTO.getProjectId()));

        User creator = userRepository.findById(requestDTO.getCreatorId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", requestDTO.getCreatorId()));

        Task task = Task.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .dueDate(requestDTO.getDueDate())
                .status(requestDTO.getStatus() != null ? requestDTO.getStatus() : TaskStatus.TODO)
                .project(project)
                .creator(creator)
                .build();

        if (requestDTO.getAssigneeId() != null) {
            User assignee = userRepository.findById(requestDTO.getAssigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", requestDTO.getAssigneeId()));
            task.setAssignee(assignee);
        }

        Task savedTask = taskRepository.save(task);
        return taskMapper.toDTO(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        return taskMapper.toDTO(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return taskMapper.toDTOList(tasks);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasksByProject(String projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return taskMapper.toDTOList(tasks);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasksByAssignee(String assigneeId) {
        List<Task> tasks = taskRepository.findByAssigneeId(assigneeId);
        return taskMapper.toDTOList(tasks);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasksByStatus(TaskStatus status) {
        List<Task> tasks = taskRepository.findByStatus(status);
        return taskMapper.toDTOList(tasks);
    }

    @Override
    public TaskResponseDTO updateTask(String id, TaskRequestDTO requestDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setDueDate(requestDTO.getDueDate());

        if (requestDTO.getStatus() != null) {
            task.setStatus(requestDTO.getStatus());
        }

        if (requestDTO.getAssigneeId() != null) {
            User assignee = userRepository.findById(requestDTO.getAssigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", requestDTO.getAssigneeId()));
            task.setAssignee(assignee);
        }

        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDTO(updatedTask);
    }

    @Override
    public void deleteTask(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        taskRepository.delete(task);
    }
}
