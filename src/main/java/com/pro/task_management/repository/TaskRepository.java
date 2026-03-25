package com.pro.task_management.repository;

import com.pro.task_management.entity.Task;
import com.pro.task_management.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    @Query("""
    SELECT t FROM Task t
    WHERE t.dueDate IS NOT NULL
    AND t.dueDate <= :time
    AND t.deadlineNotified = false
""")
    List<Task> findTasksToNotify(@Param("time") LocalDateTime time);
    List<Task> findByProjectId(String projectId);

    List<Task> findByCreatorId(String creatorId);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByProjectIdAndStatus(String projectId, TaskStatus status);

    void deleteByProjectId(String projectId);
}
