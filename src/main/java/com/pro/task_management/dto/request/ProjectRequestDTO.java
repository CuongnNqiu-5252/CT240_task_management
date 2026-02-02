package com.pro.task_management.dto.request;

import com.pro.task_management.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequestDTO {

    @NotBlank(message = "Project name is required")
    private String name;

    private String description;

    private ProjectStatus status;
}

