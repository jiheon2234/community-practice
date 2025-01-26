package com.community.domain.post.dto.req;

public record PostSearchCond(
	String title,
	String content,
	Long userId
) {
}
