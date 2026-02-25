package com.pro.task_management.service.Impl;

import com.pro.task_management.dto.request.UserRequestDTO;
import com.pro.task_management.dto.response.UserResponseDTO;
import com.pro.task_management.entity.User;
import com.pro.task_management.exception.AppException;
import com.pro.task_management.mapper.UserMapper;
import com.pro.task_management.repository.UserRepository;
import com.pro.task_management.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        if(userRepository.existsByUsername(requestDTO.getUsername()))
            throw new AppException("User existed");

        User user = userMapper.toEntity(requestDTO);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }
}
