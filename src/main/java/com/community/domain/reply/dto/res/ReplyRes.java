package com.community.domain.reply.dto.res;

import java.time.LocalDateTime;

import com.community.domain.reply.entity.Reply;
import com.community.domain.user.dto.res.SimpleUserRes;

public record ReplyRes(
	Long replyId,
	String content,
	long depth,
	String path,
	LocalDateTime createdAt,
	SimpleUserRes user
) {

	public static ReplyRes from(Reply reply) {
		return new ReplyRes(
			reply.getId(),
			reply.getContent(),
			reply.getDepth(), reply.getPath(),
			reply.getCreatedAt()
			, new SimpleUserRes(reply.getUser().getId(), reply.getUser().getProvider(), reply.getUser().getNickname())
		);
	}
}
