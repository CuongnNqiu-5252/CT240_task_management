package com.pro.task_management.service.Impl;
import com.pro.task_management.dto.request.NotificationRequestDTO;
import com.pro.task_management.dto.response.NotificationResponseDTO;
import com.pro.task_management.entity.Notification;
import com.pro.task_management.entity.Task;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final NotificationMapper notificationMapper;

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
