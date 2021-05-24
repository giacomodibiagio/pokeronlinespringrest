package com.projectpokerrest.pokerrest.web.api.exception;

public class BusinessException extends RuntimeException {
  public BusinessException(String message) {
      super(message);
  }
}
