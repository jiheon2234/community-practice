package com.community.global.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.community.global.utils.ObjectMapperUtils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ErrorResponseUtils {

	private final ObjectMapperUtils objectMapper;

	public void sendErrorResponse(HttpServletResponse response, String message, HttpStatus status) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(status.value());
		response.setCharacterEncoding("UTF-8");

		ErrorResponse errorResponse = ErrorResponse.from(message);
		String stringValue = objectMapper.toStringValue(errorResponse);
		response.getWriter().write(stringValue);
	}
}
