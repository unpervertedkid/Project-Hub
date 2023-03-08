package com.silah.projecthub.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends Exception {
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
