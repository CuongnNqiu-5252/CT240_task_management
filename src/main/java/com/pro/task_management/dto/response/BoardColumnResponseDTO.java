package com.pro.task_management.dto.response;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardColumnResponseDTO {

    private String id;
    private String name;

    private List<TaskResponseDTO> listTaskResponseDTO;
}
