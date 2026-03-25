package com.pro.task_management.service;

import com.pro.task_management.dto.request.NotificationRequestDTO;
import com.pro.task_management.dto.response.NotificationResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface NotificationService {

    NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO);

    NotificationResponseDTO getNotificationById(String id);

    List<NotificationResponseDTO> getNotificationsByUser(String userId);

    public SseEmitter subcribe(@PathVariable String userId);
    void deleteNotification(String id);
}
