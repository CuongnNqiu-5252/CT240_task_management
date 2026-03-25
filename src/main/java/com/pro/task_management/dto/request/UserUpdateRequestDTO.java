package com.pro.task_management.dto.request;

import com.pro.task_management.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserUpdateRequestDTO {
    private String password;
    private String username;
    private String email;
    private String avatar;
}
