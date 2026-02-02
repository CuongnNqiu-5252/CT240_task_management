package com.pro.task_management.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDTO {

    private String id;
    private String content;
    private LocalDateTime createdDate;
    private String userId;
    private String username;
    private String taskId;
    private String taskTitle;
}
