package com.pro.task_management.dto.request;

import com.pro.task_management.enums.ProjectRole;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMemberRequestDTO {

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Project ID is required")
    private String projectId;

    @NotNull(message = "Role is required")
    private ProjectRole role;
}
