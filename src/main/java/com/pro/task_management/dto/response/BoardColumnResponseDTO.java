package com.pro.task_management.dto.response;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardColumnResponseDTO {

    private String id;
    private String name;
    private String projectId;
    private String projectName;
}
