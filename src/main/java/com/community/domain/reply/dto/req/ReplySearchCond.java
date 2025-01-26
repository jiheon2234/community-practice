package com.community.domain.reply.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplySearchCond {

	private String lastSeenPath = "";

	@NotEmpty(message = "postId는 필수")
	@Positive
	private Long postId;

	@Min(0)
	@Max(100)
	private int limit = 10;
}
