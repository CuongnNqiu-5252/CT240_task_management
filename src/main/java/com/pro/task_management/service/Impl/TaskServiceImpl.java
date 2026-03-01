package com.pro.task_management.service.Impl;

import com.pro.task_management.dto.request.TaskRequestDTO;
import com.pro.task_management.dto.request.TaskUpdateDTO;
import com.pro.task_management.dto.response.TaskResponseDTO;
import com.pro.task_management.entity.BoardColumn;
import com.pro.task_management.entity.Project;
import com.pro.task_management.entity.Task;
import com.pro.task_management.entity.User;
import com.pro.task_management.enums.TaskStatus;
import com.pro.task_management.exception.AppException;
import com.pro.task_management.mapper.TaskMapper;
import com.pro.task_management.repository.BoardColumnRepository;
import com.pro.task_management.repository.ProjectRepository;
import com.pro.task_management.repository.TaskRepository;
import com.pro.task_management.repository.UserRepository;
import com.pro.task_management.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final BoardColumnRepository boardColumnRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
        Project project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));

        // Lấy userId từ JWT
        String userId = null;
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();

            // Lấy claim theo key
            userId = jwt.getClaim("userId");
        }

        User creator = null;
        if (userId != null) {
            creator = userRepository.findById(userId)
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Not found"));
        }

        BoardColumn boardColumn = boardColumnRepository.findById(requestDTO.getBoardColumnId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));

        Task task = Task.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .dueDate(requestDTO.getDueDate())
                .status(requestDTO.getStatus() != null ? requestDTO.getStatus() : TaskStatus.TODO)
                .project(project)
                .column(boardColumn)
                .creator(creator)
                .build();

        if (requestDTO.getAssigneeId() != null) {
            User assignee = userRepository.findById(requestDTO.getAssigneeId())
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
            task.setAssignee(assignee);
        }

        Task savedTask = taskRepository.save(task);

        String newTaskId = savedTask.getId();
        // Thêm ID của task mới vào cuối taskOrderIds của board column
        List<String> taskOrderIds = boardColumn.getTaskOrderIds();
        taskOrderIds.add(newTaskId);
        boardColumn.setTaskOrderIds(taskOrderIds);
        boardColumnRepository.save(boardColumn);

        return taskMapper.toDTO(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
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
    public TaskResponseDTO updateTask(String id, TaskUpdateDTO requestDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));

//        task.setTitle(requestDTO.getTitle());
//        task.setDescription(requestDTO.getDescription());
//        task.setDueDate(requestDTO.getDueDate());

//        if (requestDTO.getStatus() != null) {
//            task.setStatus(requestDTO.getStatus());
//        }

        if (requestDTO.getAssigneeId() != null) {
            User assignee = userRepository.findById(requestDTO.getAssigneeId())
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
            task.setAssignee(assignee);
        }

        if (requestDTO.getBoardColumnId() != null) {
            BoardColumn boardColumn = boardColumnRepository.findById(requestDTO.getBoardColumnId())
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
            task.setColumn(boardColumn);
        }

        taskMapper.updateEntityFromDTO(requestDTO, task);

        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDTO(updatedTask);
    }

    @Override
    public void deleteTask(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
        taskRepository.delete(task);
    }
}
