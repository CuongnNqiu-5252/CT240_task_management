package com.pro.task_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardColumnUpdateDTO {

    private String name;
    private List<String> taskOrderIds;
}
