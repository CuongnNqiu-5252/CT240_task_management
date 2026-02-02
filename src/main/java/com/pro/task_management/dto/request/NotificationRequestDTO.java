package com.pro.task_management.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestDTO {

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "User ID is required")
    private String userId;

    private String taskId;
}
