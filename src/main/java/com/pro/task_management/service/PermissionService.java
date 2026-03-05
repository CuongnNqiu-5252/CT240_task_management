package com.pro.task_management.service;

import com.pro.task_management.entity.User;
import com.pro.task_management.enums.ProjectRole;
import com.pro.task_management.exception.AppException;
import com.pro.task_management.repository.ProjectMemberRepository;
import com.pro.task_management.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    UserRepository userRepository;
    ProjectMemberRepository projectMemberRepository;
    public boolean isManager(String projectId) {
        // Nếu userrole là MANAGER HOẶC OWNER thì mới có quyền chỉnh sửa project
        String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        log.info(username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "User Not found"));
        boolean isOwner = projectMemberRepository.existsByProject_IdAndUser_IdAndRole(projectId, user.getId(), ProjectRole.OWNER);
        boolean isManager = projectMemberRepository.existsByProject_IdAndUser_IdAndRole(projectId, user.getId(), ProjectRole.MANAGER);
        log.info(isOwner + " " + isManager);
        return isOwner || isManager;
    }
}
