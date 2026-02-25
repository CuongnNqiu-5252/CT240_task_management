package com.pro.task_management.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AppException extends RuntimeException {
    private HttpStatus status;
    public AppException(HttpStatus status, String message) {
        super(message);
        this.status =status;
    }
}
