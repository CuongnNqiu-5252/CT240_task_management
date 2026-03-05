package com.pro.task_management.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskAssigneesRequestDTO {

    @NotNull(message = "User ID is required")
    private String userId;

    @NotNull(message = "Task ID is required")
    private String taskId;

    @NotNull(message = "Project ID is required")
    private String projectId;
}
