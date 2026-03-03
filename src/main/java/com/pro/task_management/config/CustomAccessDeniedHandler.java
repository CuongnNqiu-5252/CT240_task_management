package com.pro.task_management.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException ex
    ) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String body = """
        {
            "timestamp": "%s",
            "status": 403,
            "error": "FORBIDDEN",
            "message": "You do not have permission to access this resource",
            "path": "%s"
        }
        """.formatted(
                LocalDateTime.now(),
                request.getRequestURI()
        );

        response.getWriter().write(body);
    }
}
