package com.community.domain.post.exception;

import org.springframework.http.HttpStatus;

import com.community.global.exception.BusinessException;

public class PostNotFoundException extends BusinessException {
	public PostNotFoundException(String message) {
		super(message);
	}

	public PostNotFoundException() {
		super("존재하지 않는 글입니다.");
	}

	@Override
	protected HttpStatus getErrorStatus() {
		return HttpStatus.NOT_FOUND;
	}
}
