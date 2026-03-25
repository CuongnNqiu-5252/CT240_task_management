package com.pro.task_management.repository;

import com.pro.task_management.entity.TaskAssignees;
import com.pro.task_management.entity.TaskAssigneesId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskAssigneesRepository extends JpaRepository<TaskAssignees, TaskAssigneesId> {

    List<TaskAssignees> findByTaskId(String taskId);

    Page<TaskAssignees> findByUserId(String userId, Pageable pageable);

    void deleteByTaskId(String taskId);

    void deleteByUserId(String userId);

    boolean existsByTask_IdAndUser_Id(
            String taskId,
            String userId
    );

}
