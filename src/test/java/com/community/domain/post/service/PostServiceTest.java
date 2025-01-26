package com.community.domain.post.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.community.domain.post.dto.req.PostUpdateReq;
import com.community.domain.post.entity.Post;
import com.community.domain.post.repository.PostRepository;
import com.community.domain.supports.IntegrationTestSupport;
import com.community.domain.user.entity.Provider;
import com.community.domain.user.entity.User;
import com.community.domain.user.entity.UserRole;
import com.community.domain.user.repository.UserRepository;
import com.community.global.config.security.dto.SessionUser;
import com.community.global.exception.BusinessException;

class PostServiceTest extends IntegrationTestSupport {

	@Autowired
	private PostService postService;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@AfterEach
	void tearDown() {
		postRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName(" 사용자는 자신이 쓰지 않은게시글을 삭제할 수 없다")
	void test1() {
		SessionUser sessionUser = SessionUser.of(999L, "jiheon2234@gmail.com", Collections.emptyList());

		User user = userRepository.save(createUser("jiheon2234@gmail.com"));

		Post post = postRepository.save(createPost(user, "test", "test"));

		assertThatThrownBy(
			() -> postService.deletePost(sessionUser, post.getId())
		).isInstanceOf(BusinessException.class);

	}

	@DisplayName("제목과 계시글 수정이 가능하다")
	@Test
	public void test2() throws Exception {
		//given
		User user = userRepository.save(createUser("jiheon2234@gmail.com"));
		SessionUser sessionUser = SessionUser.of(user.getId(), user.getEmail(), null);
		Post post = postRepository.save(createPost(user, "title before change", "content before change"));

		String changeTitle = "changedTitle";
		String changedContent = "changedContent";
		PostUpdateReq req = new PostUpdateReq(changeTitle, changedContent);
		//when
		postService.updatePost(post.getId(), sessionUser, req);

		//then
		assertThat(postRepository.findAll().get(0)).extracting("title", "content")
			.contains(changeTitle, changedContent);
	}

	private Post createPost(User user, String title, String content) {
		return Post.builder()
			.title(title)
			.content(content)
			.user(user)
			.build();
	}

	private User createUser(String email) {
		return User.builder()
			.provider(Provider.BASIC)
			.roles(List.of(UserRole.USER))
			.email(email)
			.password("password")
			.nickname("nickname")
			.sub("aaaaaa")
			.build();
	}

}