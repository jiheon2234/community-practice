package com.community.global.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
@ControllerAdvice
public class ExceptionController {

	// @ExceptionHandler
	// public ResponseEntity<ErrorResponse> handleAllException(Exception e) {
	// 	log.error("Exception =", e);
	// 	ErrorResponse errorResponse = ErrorResponse.from(e.getMessage());
	// 	return ResponseEntity.status(419).body(errorResponse);
	// }

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleFieldException(MethodArgumentNotValidException e) {
		log.error("Exception =", e);
		Map<String, String> validations = new HashMap<>();
		for (val fieldError : e.getFieldErrors()) {
			validations.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		ErrorResponse errorResponse = ErrorResponse.of("잘못된 요청입니다", validations);

		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
		log.error("Exception =", e);
		ErrorResponse errorResponse = ErrorResponse.from(e.getMessage());
		return ResponseEntity.status(e.getErrorStatus()).body(errorResponse);
	}

	// @ExceptionHandler(AuthorizationDeniedException.class)
	// public String test(AccessDeniedException e) {
	// 	return e.getMessage();
	// }
}
