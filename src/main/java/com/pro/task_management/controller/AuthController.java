package com.pro.task_management.controller;

import com.pro.task_management.dto.request.AuthRequestDTO;
import com.pro.task_management.dto.request.ChangePasswordRequestDTO;
import com.pro.task_management.dto.request.UserRequestDTO;
import com.pro.task_management.dto.response.ApiResponse;
import com.pro.task_management.dto.response.AuthResponseDTO;
import com.pro.task_management.dto.response.UserResponseDTO;
import com.pro.task_management.service.AuthService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        UserResponseDTO userResponse = authService.createUser(requestDTO);
        return new ResponseEntity<>(ApiResponse.<UserResponseDTO>builder().data(userResponse).message("User created successfully").build(), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody AuthRequestDTO request) {
        AuthResponseDTO authResponse = authService.login(request);
        return new ResponseEntity<>(ApiResponse.<AuthResponseDTO>builder()
                .data(authResponse)
                .message("Login successfully")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequestDTO requestDTO) {
        authService.changePassword(requestDTO);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .message("Password changed successfully")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        authService.logout();
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .message("Logout successfully")
                .build());
    }
}
