package com.pro.task_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordRequestDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    String username;

    @NotBlank(message = "Old password is required")
    @Size(min = 5, message = "Old password must be at least 5 characters")
    String oldPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 5, message = "New password must be at least 5 characters")
    String newPassword;
}
