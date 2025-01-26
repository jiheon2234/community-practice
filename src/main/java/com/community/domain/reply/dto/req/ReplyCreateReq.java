package com.community.domain.reply.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ReplyCreateReq(
	@NotEmpty(message = "게시글 ID는 필수입니다")
	Long postId,
	Long parentId,
	@NotEmpty(message = "내용은 필수입니다")
	@Size(min = 2, message = "내용은 최소한 2자 이상이어야 합니다")
	String content
) {
}
