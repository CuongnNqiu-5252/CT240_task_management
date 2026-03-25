package com.pro.task_management.service.Impl;
import com.pro.task_management.dto.request.NotificationRequestDTO;
import com.pro.task_management.dto.response.NotificationResponseDTO;
import com.pro.task_management.entity.Notification;
import com.pro.task_management.entity.Task;
import com.pro.task_management.entity.TaskAssignees;
import com.pro.task_management.entity.User;
import com.pro.task_management.exception.AppException;
import com.pro.task_management.mapper.NotificationMapper;
import com.pro.task_management.repository.NotificationRepository;
import com.pro.task_management.repository.TaskRepository;
import com.pro.task_management.repository.UserRepository;
import com.pro.task_management.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final NotificationMapper notificationMapper;

    private final Map<String, List<SseEmitter>> emitters = new ConcurrentHashMap<>();
    public void createAndSendNotification(Task task) {

        if (task.getTaskAssignees() == null || task.getTaskAssignees().isEmpty()) {
            return;
        }

        String content = "Task sắp tới hạn: " + task.getTitle();

        for (TaskAssignees user : task.getTaskAssignees()) {

            // ❗ chống duplicate theo từng user
            boolean exists = notificationRepository
                    .existsByTask_IdAndUser_IdAndContent(
                            task.getId(),
                            user.getUser().getId(),
                            content
                    );

            if (exists) continue;

            Notification notification = Notification.builder()
                    .content(content)
                    .task(task)
                    .user(user.getUser())
                    .build();

            notificationRepository.save(notification);

            // map DTO

            // 🔔 gửi SSE cho từng user
            sendNotification(user.getUser().getId(), notificationMapper.toDTO(notification));
        }
    }

    @Override
    public SseEmitter subcribe(String userId) {

        SseEmitter emitter = new SseEmitter(0L);

        emitters.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>())
                .add(emitter);

        emitter.onCompletion(() -> removeEmitter(userId, emitter));
        emitter.onTimeout(() -> removeEmitter(userId, emitter));

        return emitter;
    }

    public void sendNotification(String userId, Object data) {

        List<SseEmitter> userEmitters = emitters.get(userId);

        if (userEmitters == null) return;

        for (SseEmitter emitter : userEmitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(data));
            } catch (Exception e) {
                emitter.complete();
            }
        }
    }

    private void removeEmitter(String userId, SseEmitter emitter) {
        List<SseEmitter> userEmitters = emitters.get(userId);
        if (userEmitters != null) {
            userEmitters.remove(emitter);
        }
    }


    @Override
    public NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));

        Notification notification = Notification.builder()
                .content(requestDTO.getContent())
                .user(user)
                .build();

        if (requestDTO.getTaskId() != null) {
            Task task = taskRepository.findById(requestDTO.getTaskId())
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
            notification.setTask(task);
        }

        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationResponseDTO getNotificationById(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
        return notificationMapper.toDTO(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> getNotificationsByUser(String userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        return notificationMapper.toDTOList(notifications);
    }


    @Override
    public void deleteNotification(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
        notificationRepository.delete(notification);
    }
}
