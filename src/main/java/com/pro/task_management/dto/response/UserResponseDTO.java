package com.pro.task_management.dto.response;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private String id;
    private String username;
    private String email;
    private String avatar;
    private Boolean deleted;
    private Boolean isAdmin;
}
