package com.pro.task_management.dto.request;

import com.pro.task_management.enums.Role;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserUpdateRequestDTO {
    private String username;
    private String email;
    private String avatar;
    private Role role;
}
