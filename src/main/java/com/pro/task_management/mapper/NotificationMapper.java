package com.pro.task_management.mapper;

import com.pro.task_management.dto.response.NotificationResponseDTO;
import com.pro.task_management.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "task.title", target = "taskTitle")
    NotificationResponseDTO toDTO(Notification notification);

    List<NotificationResponseDTO> toDTOList(List<Notification> notifications);
}
