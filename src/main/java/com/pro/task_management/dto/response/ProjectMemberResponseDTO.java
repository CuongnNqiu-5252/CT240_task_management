package com.pro.task_management.dto.response;

import com.pro.task_management.enums.ProjectRole;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMemberResponseDTO {

    private String userId;
    private String projectId;
    private String username;
    private String projectName;
    private ProjectRole role;
}
