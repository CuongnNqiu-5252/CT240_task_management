package com.pro.task_management.dto;

import com.pro.task_management.dto.response.CommentResponseDTO;
import com.pro.task_management.enums.CommentEventType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentEvent {
    CommentResponseDTO payload;
    CommentEventType type;
}
