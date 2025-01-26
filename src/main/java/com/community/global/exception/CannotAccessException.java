package com.community.global.exception;

import org.springframework.http.HttpStatus;

public class CannotAccessException extends BusinessException {
	public CannotAccessException(String message) {
		super(message);
	}

	public CannotAccessException() {
		super("접근 권한이없습니다.");
	}

	@Override
	protected HttpStatus getErrorStatus() {
		return HttpStatus.FORBIDDEN;
	}
}
