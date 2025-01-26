package com.community.domain.post.dto.req;

import jakarta.validation.constraints.NotBlank;

public record PostUpdateReq(
	@NotBlank(message = "제목필수")
	String title,
	@NotBlank(message = "내용필수")
	String content) {
}
