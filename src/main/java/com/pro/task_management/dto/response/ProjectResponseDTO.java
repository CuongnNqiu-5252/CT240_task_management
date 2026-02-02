package com.pro.task_management.dto.response;

import com.pro.task_management.enums.ProjectStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDTO {

    private String id;
    private String name;
    private String description;
    private ProjectStatus status;
}
