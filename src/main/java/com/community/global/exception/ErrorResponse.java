package com.community.global.exception;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private final String message;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Map<String, String> validation;

	private ErrorResponse(String message, Map<String, String> validation) {
		this.message = message;
		this.validation = validation;
	}

	public static ErrorResponse from(String message) {
		return new ErrorResponse(message, null);
	}

	public static ErrorResponse of(String message, Map<String, String> validation) {
		return new ErrorResponse(message, validation);
	}
}
