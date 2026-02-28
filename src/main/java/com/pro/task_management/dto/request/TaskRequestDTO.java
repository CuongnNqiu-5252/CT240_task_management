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
public class TaskRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private LocalDateTime dueDate;

    private TaskStatus status;

    @NotNull(message = "Project ID is required")
    private String projectId;

    @NotNull(message = "Board column ID is required")
    private String boardColumnId;

    private String assigneeId;

    @NotNull(message = "Creator ID is required")
    private String creatorId;
}
