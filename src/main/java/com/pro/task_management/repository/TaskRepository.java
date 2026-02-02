package com.pro.task_management.repository;

import com.pro.task_management.entity.Task;
import com.pro.task_management.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    List<Task> findByProjectId(String projectId);

    List<Task> findByAssigneeId(String assigneeId);

    List<Task> findByCreatorId(String creatorId);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByProjectIdAndStatus(String projectId, TaskStatus status);

    void deleteByProjectId(String projectId);
}
