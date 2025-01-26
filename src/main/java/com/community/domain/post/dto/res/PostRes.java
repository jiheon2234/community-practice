package com.community.domain.post.dto.res;

import java.time.LocalDateTime;

public record PostRes(
	Long id,
	PostUserRes user,
	String title,
	String content,
	LocalDateTime createdAt,
	boolean isDelete
) {
}
