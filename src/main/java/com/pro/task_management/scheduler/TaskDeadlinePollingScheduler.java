package com.pro.task_management.scheduler;

import com.pro.task_management.dto.response.NotificationResponseDTO;
import com.pro.task_management.entity.Notification;
import com.pro.task_management.entity.Task;
import com.pro.task_management.entity.TaskAssignees;
import com.pro.task_management.entity.User;
import com.pro.task_management.event.TaskDeadlineEvent;
import com.pro.task_management.repository.NotificationRepository;
import com.pro.task_management.repository.TaskRepository;
import com.pro.task_management.service.Impl.NotificationServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskDeadlinePollingScheduler {

    private final TaskRepository taskRepository;
    private final NotificationServiceImpl notificationService;

    @Scheduled(fixedRate = 10000) // mỗi 10s
    @Transactional
    public void checkDeadline() {

        LocalDateTime nowPlus24h = LocalDateTime.now().plusHours(24);

        List<Task> tasks = taskRepository.findTasksToNotify(nowPlus24h);

        for (Task task : tasks) {

            log.info("Processing task {}", task.getId());

            notificationService.createAndSendNotification(task);

            task.setDeadlineNotified(true); // 👈 tránh duplicate
        }
    }
}