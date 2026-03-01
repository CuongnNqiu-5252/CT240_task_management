package com.pro.task_management.dto.request;

import com.pro.task_management.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectUpdateDTO {

    private String name;
    private String description;
    private ProjectStatus status;
    private List<String> columnOrderIds;
}

