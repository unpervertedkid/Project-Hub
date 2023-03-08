package com.silah.projecthub.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
public class InvalidProjectException extends Exception {
    public InvalidProjectException(String message) {
        super(message);
    }
}
