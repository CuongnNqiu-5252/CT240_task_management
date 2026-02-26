package com.pro.task_management.service.Impl;

import com.pro.task_management.dto.request.UserRequestDTO;
import com.pro.task_management.dto.request.UserUpdateRequestDTO;
import com.pro.task_management.dto.response.PageResponse;
import com.pro.task_management.dto.response.Pagination;
import com.pro.task_management.dto.response.UserResponseDTO;
import com.pro.task_management.entity.User;
import com.pro.task_management.enums.Role;
import com.pro.task_management.exception.AppException;
import com.pro.task_management.mapper.UserMapper;
import com.pro.task_management.repository.UserRepository;
import com.pro.task_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        if(userRepository.existsByUsername(requestDTO.getUsername()))
            throw new AppException(HttpStatus.CONFLICT,"User existed");
        User user = userMapper.toEntity(requestDTO);
        user.setDeleted(false);
        user.setRole(Role.USER);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<List<UserResponseDTO>> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> users = userRepository.findAll(pageable);
        List<UserResponseDTO> userResponseDTOS = users.getContent()
                .stream().map(userMapper::toDTO)
                .toList();
        Pagination pagination = Pagination.builder()
                .size(size)
                .totalElements(users.getTotalElements())
                .totalPages(users.getTotalPages())
                .build();
        return PageResponse.<List<UserResponseDTO>>builder()
                .data(userResponseDTOS)
                .pagination(pagination)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllActiveUsers() {
        List<User> users = userRepository.findByDeletedFalse();
        return userMapper.toDTOList(users);
    }

    @Override
    public UserResponseDTO updateUser(String id, UserUpdateRequestDTO requestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
        userMapper.toUpdateDTO(requestDTO, user);
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
        userRepository.delete(user);
    }

    @Override
    public void softDeleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Not found"));
        user.setDeleted(true);
        userRepository.save(user);
    }


}
