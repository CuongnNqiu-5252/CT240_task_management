package com.pro.task_management.dto.response;

import com.pro.task_management.enums.ProjectStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseWithMembersDTO {

    private String id;
    private String name;
    private String description;
    private ProjectStatus status;
    private List<ProjectMemberResponseDTO> members;
    private String owner;
}
