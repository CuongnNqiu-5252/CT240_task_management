package com.pro.task_management.service;


import com.pro.task_management.dto.request.UserRequestDTO;
import com.pro.task_management.dto.request.UserUpdateRequestDTO;
import com.pro.task_management.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO requestDTO);

    UserResponseDTO getUserById(String id);

    List<UserResponseDTO> getAllUsers();

    List<UserResponseDTO> getAllActiveUsers();

    UserResponseDTO updateUser(String id, UserUpdateRequestDTO requestDTO);

    void deleteUser(String id);

    void softDeleteUser(String id);
}
