package com.pro.task_management.dto.response;

import com.pro.task_management.enums.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDTO {

    private String id;
    private String title;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime dueDate;
    private TaskStatus status;
    private String projectId;
    private String projectName;
    private String assigneeId;
    private String assigneeName;
    private String creatorId;
    private String creatorName;
}
