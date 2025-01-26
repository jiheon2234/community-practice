package com.community.domain.post.service;

import org.springframework.stereotype.Component;

import com.community.domain.post.dto.req.PostCreateReq;
import com.community.domain.post.dto.res.PostRes;
import com.community.domain.post.dto.res.PostUserRes;
import com.community.domain.post.dto.res.SimplePostRes;
import com.community.domain.post.entity.Post;
import com.community.domain.user.entity.User;

@Component
public class PostMapper {

	public Post toEntity(User user, PostCreateReq postCreateReq) {
		Post post = Post.builder()
			.user(user)
			.title(postCreateReq.title())
			.content(postCreateReq.content())
			.build();

		return post;
	}

	public PostRes toPostRes(Post post) {
		PostRes postRes = new PostRes(post.getId(),
			new PostUserRes(post.getUser().getId(), post.getUser().getNickname()),
			post.getTitle(),
			post.getContent(),
			post.getCreatedAt(),
			post.isDeleted()
		);
		return postRes;
	}

	public SimplePostRes toSimplePostRes(Post post) {
		return new SimplePostRes(
			post.getTitle(),
			new PostUserRes(post.getUser().getId(), post.getUser().getNickname()));
	}

}
