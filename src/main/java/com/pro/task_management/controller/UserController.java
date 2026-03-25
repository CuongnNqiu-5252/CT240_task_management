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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        UserResponseDTO response = userService.createUser(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PatchMapping("/{id}/restore")
    public ResponseEntity<UserResponseDTO> restoreUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.restoreUser(id));
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
    @GetMapping("/AllUser")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsersAD(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorities: " + auth.getAuthorities());
        PageResponse<List<UserResponseDTO>> serviceResponse = userService.getAllUsersAD(page, size);
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
//    @PreAuthorize("@securityService.isOwner(#id) or hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(
            @PathVariable String id,
            @RequestBody UserUpdateRequestDTO requestDTO) {

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
