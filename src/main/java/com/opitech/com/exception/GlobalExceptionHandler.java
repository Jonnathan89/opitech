/**
 * 
 */
package com.opitech.com.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author psych
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(HeroNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNoSuchElement(HeroNotFoundException ex,
			HttpServletRequest request) {
		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.NOT_FOUND.value());
		error.put("error", "Not Found");
		error.put("message", ex.getMessage());
		error.put("path", request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex, HttpServletRequest request) {
		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.put("error", "Internal server error");
		error.put("message", ex.getMessage());
		error.put("path", request.getRequestURI());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	@ExceptionHandler(HeroAlreadyExistsException.class)
	public ResponseEntity<Map<String, Object>> handleGeneric(HeroAlreadyExistsException ex ,  HttpServletRequest request) {
		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.CONFLICT.value());
		error.put("error", "The name value is already in the DB.");
		error.put("message", ex.getMessage());
		error.put("path", request.getRequestURI());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleGeneric(IllegalArgumentException ex, HttpServletRequest request) {
		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.BAD_REQUEST.value());
		error.put("error", "The name value is failed.");
		error.put("message", ex.getMessage());
		error.put("path", request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
