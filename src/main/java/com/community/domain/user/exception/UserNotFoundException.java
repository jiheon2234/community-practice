package com.community.domain.user.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.community.global.exception.BusinessException;

public class UserNotFoundException extends BusinessException {

	public UserNotFoundException() {
		super("CANNOT_FIND_USER");
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	@Override
	protected HttpStatus getErrorStatus() {
		return NOT_FOUND;
	}
}
