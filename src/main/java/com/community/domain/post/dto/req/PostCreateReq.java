package com.community.domain.post.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostCreateReq(
	@NotBlank(message = "제목필수")
	@Size(min = 2, message = "제목은 최소한 2자이상")
	String title,

	@NotBlank(message = "내용필수")
	@Size(min = 2, message = "내용은 최소한 2자이상")
	String content

) {
}
