package com.community.domain.reply.exception;

import org.springframework.http.HttpStatus;

import com.community.global.exception.BusinessException;

public class ReplyNotFoundException extends BusinessException {

	public ReplyNotFoundException(String message) {
		super(message);
	}

	public ReplyNotFoundException() {
		super("해당 답글은 존재하지 않습니다");
	}

	@Override
	protected HttpStatus getErrorStatus() {
		return HttpStatus.NOT_FOUND;
	}
}
