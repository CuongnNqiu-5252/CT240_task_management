package com.pro.task_management.repository;

import com.pro.task_management.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

    List<Notification> findByUserId(String userId);

    List<Notification> findByTaskId(String taskId);

    void deleteByUserId(String userId);

    void deleteByTaskId(String taskId);
}
