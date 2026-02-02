package com.pro.task_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDTO {

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Task ID is required")
    private String taskId;

    @NotNull(message = "User ID is required")
    private String userId;
}
