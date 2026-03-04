package com.pro.task_management.utils;

import com.pro.task_management.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityUtils {

    public static String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication == null) && !authentication.isAuthenticated()) {
            throw new AppException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();

        assert jwt != null;
        Object userId = jwt.getClaim("userId");
        if (userId instanceof String) {
            return (String) userId;
        } else {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid token: userId claim is missing or not a string");
        }
    }

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();
        assert jwt != null;
        return jwt.getSubject();
    }
}
