package com.projectpokerrest.pokerrest.web.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

  public class TavoloNotFoundException extends RuntimeException {
    public TavoloNotFoundException(String message) {
        super(message);
    }
}
