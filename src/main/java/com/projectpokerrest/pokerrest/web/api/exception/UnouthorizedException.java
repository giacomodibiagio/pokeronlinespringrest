package com.projectpokerrest.pokerrest.web.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnouthorizedException extends RuntimeException {
    public UnouthorizedException(String message) {
        super(message);
    }
}
