package com.pro.task_management.utils;

import com.pro.task_management.dto.response.NotificationResponseDTO;
import com.pro.task_management.entity.Notification;
import com.pro.task_management.entity.Task;
import com.pro.task_management.entity.TaskAssignees;
import com.pro.task_management.event.TaskDeadlineEvent;
import com.pro.task_management.mapper.NotificationMapper;
import com.pro.task_management.repository.NotificationRepository;
import com.pro.task_management.service.Impl.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskDeadlineListener {

    private final NotificationServiceImpl notificationService;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @EventListener
    public void handleTaskDeadline(TaskDeadlineEvent event) {

        Task task = event.getTask();

        for (TaskAssignees user : task.getTaskAssignees()) {

            Notification notification = Notification.builder()
                    .content("Task sắp tới hạn: " + task.getTitle())
                    .user(user.getUser())
                    .task(task)
                    .build();
            boolean exists = notificationRepository.existsByTask_IdAndUser_IdAndContent(
                    task.getId(),
                    user.getUser().getId(),
                    "Task sắp tới hạn: " + task.getTitle()
            );

            if (!exists) {
                notificationRepository.save(notification);
            }


            notificationService.sendNotification(String.valueOf(user.getId()), notificationMapper.toDTO(notification));
        }
    }
}