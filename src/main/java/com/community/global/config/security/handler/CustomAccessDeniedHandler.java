package com.community.global.config.security.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.community.global.exception.ErrorResponseUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private static final String MESSAGE = "AccessDenied ㅠㅠㅠㅠ";
	private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
	private final ErrorResponseUtils errorResponseUtils;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.error("# accessDenied : {}", accessDeniedException.getMessage());
		errorResponseUtils.sendErrorResponse(response, MESSAGE, HTTP_STATUS);
	}
}
