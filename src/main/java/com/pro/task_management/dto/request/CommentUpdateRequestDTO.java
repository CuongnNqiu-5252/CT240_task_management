package com.pro.task_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentUpdateRequestDTO {
    @NotBlank(message = "Content is required")
    private String content;
}
