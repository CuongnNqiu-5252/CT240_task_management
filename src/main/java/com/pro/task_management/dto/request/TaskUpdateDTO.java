package com.pro.task_management.dto.request;

import com.pro.task_management.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskUpdateDTO {
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private TaskStatus status;
    private String projectId;
    private String boardColumnId;
    private String assigneeId;
}
