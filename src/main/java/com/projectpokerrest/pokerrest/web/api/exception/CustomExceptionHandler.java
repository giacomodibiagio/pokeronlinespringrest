package com.projectpokerrest.pokerrest.web.api.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());

		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, headers, status);
	}

	@ExceptionHandler(value = {TavoloNotFoundException.class,  UtenteNotFoundException.class})
	protected ResponseEntity<Object> notFoundConflict(
			RuntimeException ex, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("message",ex.getMessage());
		body.put("status", HttpStatus.NOT_FOUND.value());

		String bodyOfResponse = ex.getMessage();

		return handleExceptionInternal(ex, body,
				new HttpHeaders(), HttpStatus.NOT_FOUND , request);
	}
	@ExceptionHandler(value = {BusinessException.class })
	protected ResponseEntity<Object> businessConflict(
			RuntimeException ex, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("message",ex.getMessage());
		body.put("status", HttpStatus.BAD_REQUEST.value());

		String bodyOfResponse = ex.getMessage();

		return handleExceptionInternal(ex, body,
				new HttpHeaders(), HttpStatus.BAD_REQUEST , request);
	}
}

