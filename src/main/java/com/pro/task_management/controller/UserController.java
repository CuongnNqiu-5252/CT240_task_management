package com.pro.task_management.controller;

import com.pro.task_management.dto.request.UserRequestDTO;
import com.pro.task_management.dto.request.UserUpdateRequestDTO;
import com.pro.task_management.dto.response.ApiResponse;
import com.pro.task_management.dto.response.PageResponse;
import com.pro.task_management.dto.response.ProjectResponseDTO;
import com.pro.task_management.dto.response.UserResponseDTO;
import com.pro.task_management.service.CloudinaryService;
import com.pro.task_management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final CloudinaryService cloudinaryService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        UserResponseDTO response = userService.createUser(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id) {
        UserResponseDTO response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<List<UserResponseDTO>> serviceResponse = userService.getAllUsers(page, size);
        return ResponseEntity.ok(ApiResponse.<List<UserResponseDTO>>builder()
                .message("Get successes")
                .data(serviceResponse.getData())
                .build());
    }

    @GetMapping("/active")
    public ResponseEntity<List<UserResponseDTO>> getAllActiveUsers() {
        List<UserResponseDTO> response = userService.getAllActiveUsers();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(
            @PathVariable String id,
            @ModelAttribute UserUpdateRequestDTO requestDTO,
            @RequestParam(value = "image", required = false) MultipartFile file) {

        String imageUrl = "";

        // Nếu có file thì upload
        if (file != null && !file.isEmpty()) {
            Map data = cloudinaryService.upload(file);
            imageUrl = data.get("secure_url").toString();
        }

        requestDTO.setAvatar(imageUrl);

        UserResponseDTO response = userService.updateUser(id, requestDTO);
        return ResponseEntity.ok(ApiResponse.<UserResponseDTO>builder()
                .data(response)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/soft-delete")
    public ResponseEntity<Void> softDeleteUser(@PathVariable String id) {
        userService.softDeleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
