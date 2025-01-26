package com.community.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

	protected BusinessException(String message) {
		super(message);
	}

	protected abstract HttpStatus getErrorStatus();

}