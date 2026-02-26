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

    private UserResponseDTO user;
    private ProjectResponseDTO project;
    private ProjectRole role;
}
