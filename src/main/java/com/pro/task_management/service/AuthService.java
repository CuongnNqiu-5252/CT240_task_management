package com.pro.task_management.service;

import com.pro.task_management.dto.request.UserRequestDTO;
import com.pro.task_management.dto.response.UserResponseDTO;

public interface AuthService {
    UserResponseDTO createUser(UserRequestDTO requestDTO);
}
