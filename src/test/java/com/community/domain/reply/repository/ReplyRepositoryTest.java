package com.community.domain.reply.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.community.domain.post.entity.Post;
import com.community.domain.post.repository.PostRepository;
import com.community.domain.reply.entity.Reply;
import com.community.domain.supports.RepositoryTestSupport;
import com.community.domain.user.entity.Provider;
import com.community.domain.user.entity.User;
import com.community.domain.user.entity.UserRole;
import com.community.domain.user.repository.UserRepository;

import lombok.val;

class ReplyRepositoryTest extends RepositoryTestSupport {

	@Autowired
	private ReplyRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@DisplayName("postId와 path를 사용해서 계층구조를 만들 수 있다.")
	@Test
	public void test1() throws Exception {
		//given
		User user1 = userRepository.save(createUser("a@a.com"));
		User user2 = userRepository.save(createUser("b@b.com"));
		Post post = postRepository.save(createPost(user1, "title"));

		//when
		Reply parent = null;
		long l = 0L;
		for (int i = 0; i < 12; i++) {
			Reply reply = createReply(user2, post, parent, "Content" + i, l + i);
			repository.save(reply);
			parent = reply;
		}

		//then
		Reply tmp = parent;
		while (tmp.getParrentReply() != null) {
			parent = tmp.getParrentReply();
			System.out.println("tmp = " + tmp.getPath() + " " + tmp.getDepth());
			// System.out.println("parent = " + parent.getPath());
			assertThat(tmp.getPath().startsWith(parent.getPath()));
			assertThat(tmp.getDepth() - 1).isEqualTo(parent.getDepth());
			tmp = parent;
		}
		assertThat(tmp.getDepth()).isZero();
	}

	@DisplayName("path를 통해 조회할때 하위 댓글은 시간보다는 부모 댓글의 순서에 따라 먼져 조회된다.")
	@Test
	public void test2() throws Exception {
		//given
		User user1 = userRepository.save(createUser("a@a.com"));
		User user2 = userRepository.save(createUser("b@b.com"));
		Post post = postRepository.save(createPost(user1, "title"));

		long l = 0;
		Reply firstReply = repository.save(createReply(user1, post, null, "first", l + 1));
		Reply secondReply = repository.save(createReply(user2, post, null, "second", l + 2));
		l = 2;
		for (int i = 0; i < 10; i++) {
			l += 1;
			repository.save(
				createReply(user2, post, secondReply, "second reply's reply" + i, l));
		}
		for (int i = 0; i < 10; i++) {
			l += 1;
			repository.save(
				createReply(user1, post, firstReply, "first reply's reply" + i, l));
		}
		//when

		repository.findAll()
			.forEach(reply -> System.out.println("reply.getPath() = " + reply.getPath()));

		List<Reply> replies = repository.getRepliesByPostIdAndLastSeenPath(post.getId(), "", Pageable.ofSize(9));

		//then
		for (val reply : replies) {
			assertThat(reply.getUser()).isEqualTo(user1);
		}

	}

	private Reply createReply(User user, Post post, Reply parrentReply, String content, long l) {
		return Reply.
			builder()
			.user(user)
			.post(post)
			.parrentReply(parrentReply)
			.content(content)
			.mills(l)
			.build();
	}

	private User createUser(String email) {
		return userRepository.save(User.builder()
			.provider(Provider.BASIC)
			.roles(List.of(UserRole.USER))
			.email(email)
			.password("password")
			.nickname("nickname")
			.sub("aaaaaa")
			.build());
	}

	private Post createPost(User user, String title) {
		return
			Post.builder()
				.user(user)
				.title(title)
				.content("postConent")
				.build();
	}

}
