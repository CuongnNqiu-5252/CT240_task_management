package com.pro.task_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardColumnRequestDTO {

    @NotBlank(message = "Column name is required")
    private String name;

    @NotNull(message = "Project ID is required")
    private String projectId;
}
