package com.community.domain.user;

import org.springframework.http.HttpStatus;

import com.community.global.exception.BusinessException;

public class InvalidCredentialException extends BusinessException {

	protected InvalidCredentialException(String message) {
		super(message);
	}

	public InvalidCredentialException() {
		super("사용자 인증에 실패했습니다");
	}

	@Override
	protected HttpStatus getErrorStatus() {
		return null;
	}
}
