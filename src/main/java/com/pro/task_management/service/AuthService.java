package com.pro.task_management.service;

import com.pro.task_management.dto.request.AuthRequestDTO;
import com.pro.task_management.dto.request.ChangePasswordRequestDTO;
import com.pro.task_management.dto.request.UserRequestDTO;
import com.pro.task_management.dto.response.AuthResponseDTO;
import com.pro.task_management.dto.response.UserResponseDTO;

public interface AuthService {
    UserResponseDTO createUser(UserRequestDTO requestDTO);
    AuthResponseDTO login(AuthRequestDTO request);
    void changePassword(ChangePasswordRequestDTO requestDTO);
    void logout();
}
