package com.community.domain.reply.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.community.domain.post.entity.Post;
import com.community.domain.user.entity.Provider;
import com.community.domain.user.entity.User;
import com.community.domain.user.entity.UserRole;

class ReplyTest {

	@DisplayName("댓글 생성 시, path와 depth가 정확하게 추가된다.")
	@Test
	void test1() {
		User user = User.builder()
			.email("email")
			.password("password")
			.sub("sub")
			.provider(Provider.BASIC)
			.nickname("nickname")
			.roles(List.of(UserRole.USER))
			.build();

		Post post = Post.builder()
			.user(user)
			.title("postTitle")
			.content("postConent")
			.build();

		Long parentMills = 0L;
		Reply parentReply = Reply.builder()
			.parrentReply(null)
			.post(post)
			.content("parentReply")
			.mills(parentMills)
			.post(post)
			.build();

		Reply childReply = Reply.builder()
			.parrentReply(parentReply)
			.content("childReply")
			.mills(parentMills + 3L)
			.user(user)
			.post(post)
			.build();

		assertThat(parentReply.getDepth()).isZero();
		assertThat(childReply.getDepth()).isOne();
		assertThat(parentReply.getPath()).isEqualTo("가가가가/");
		assertThat(parentReply.getPath().startsWith(childReply.getPath()));

	}
}