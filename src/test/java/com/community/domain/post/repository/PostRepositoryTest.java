package com.community.domain.post.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.community.domain.post.dto.req.PostSearchCond;
import com.community.domain.post.entity.Post;
import com.community.domain.supports.RepositoryTestSupport;
import com.community.domain.user.entity.Provider;
import com.community.domain.user.entity.User;
import com.community.domain.user.entity.UserRole;
import com.community.domain.user.repository.UserRepository;

class PostRepositoryTest extends RepositoryTestSupport {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("PostSearchCond에 따라 post를 조건별로 조회하는 성공케이스")
	void test1() throws Exception {
		User user = userRepository.save(User.builder()
			.provider(Provider.BASIC)
			.roles(List.of(UserRole.USER))
			.email("jiheon2234@gmail.com")
			.password("password")
			.nickname("nickname")
			.sub("aaaaaa")
			.build());

		String title1 = "title";
		String content1 = "content";
		int l = 9;
		for (int i = 0; i < l; i++) {
			postRepository.save(makePost(user, title1 + i, content1 + i));
		}

		String title2 = "제목";
		String content2 = "내용";

		for (int i = 0; i < l; i++) {
			postRepository.save(makePost(user, title2 + i, content2 + i));
		}
		PostSearchCond cond = new PostSearchCond(title1, content1, null);
		PageRequest pageable = PageRequest.of(0, 10);
		Page<Post> posts = postRepository.findPostsBySearchCond(pageable, cond);

		assertThat(posts.getContent())
			.hasSize(l)
			.extracting("title", "content")
			.containsExactlyInAnyOrder(
				tuple("title0", "content0"),
				tuple("title1", "content1"),
				tuple("title2", "content2"),
				tuple("title3", "content3"),
				tuple("title4", "content4"),
				tuple("title5", "content5"),
				tuple("title6", "content6"),
				tuple("title7", "content7"),
				tuple("title8", "content8")
			);

	}

	@DisplayName("post delete할때 삭제되는 대신  isDeleted마커가 true로 설정된다.")
	@Test
	void test2() throws Exception {
		User user = userRepository.save(User.builder()
			.provider(Provider.BASIC)
			.roles(List.of(UserRole.USER))
			.email("jiheon2234@gmail.com")
			.password("password")
			.nickname("nickname")
			.sub("aaaaaa")
			.build());

		Post post = postRepository.save(makePost(user, "title", "content"));
		assertThat(post.isDeleted()).isFalse();
		postRepository.delete(post);

		assertThat(postRepository.findAll().get(0).isDeleted()).isTrue();

	}

	private Post makePost(User user, String title, String content) {
		return Post.builder()
			.title(title)
			.content(content)
			.user(user)
			.build();
	}

}