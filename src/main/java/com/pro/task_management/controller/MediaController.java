package com.pro.task_management.controller;

import com.pro.task_management.dto.response.ApiResponse;
import com.pro.task_management.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaController {
    private final CloudinaryService cloudinaryService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadImage(
            @RequestParam("image") MultipartFile file) {
        Map data = cloudinaryService.upload(file);
        String imageUrl = data.get("secure_url").toString();
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .message("Upload success")
                .data(imageUrl)
                .build());
    }
}
