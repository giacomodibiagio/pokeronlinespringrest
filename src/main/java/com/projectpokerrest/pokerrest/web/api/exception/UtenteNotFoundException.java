package com.projectpokerrest.pokerrest.web.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UtenteNotFoundException extends RuntimeException {
    public UtenteNotFoundException(String message) {
        super(message);
    }
}
