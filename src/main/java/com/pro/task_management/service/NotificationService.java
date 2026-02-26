package com.pro.task_management.service;

import com.pro.task_management.dto.request.NotificationRequestDTO;
import com.pro.task_management.dto.response.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {

    NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO);

    NotificationResponseDTO getNotificationById(String id);

    List<NotificationResponseDTO> getNotificationsByUser(String userId);

    void deleteNotification(String id);
}
