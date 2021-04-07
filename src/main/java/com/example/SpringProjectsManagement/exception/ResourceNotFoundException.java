package com.example.SpringProjectsManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE="Item not found with id - ";

    public ResourceNotFoundException(long id) {
        super(MESSAGE+id);
    }

    public ResourceNotFoundException(long id, Throwable throwable) {
        super(MESSAGE+id, throwable);
    }
}
