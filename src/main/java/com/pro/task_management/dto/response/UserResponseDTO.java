package com.pro.task_management.dto.response;

import com.pro.task_management.enums.Role;
import lombok.*;

import java.util.Set;


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
    private Role role;
}
