package com.community.domain.post.service;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.community.domain.post.dto.req.PostCreateReq;
import com.community.domain.post.dto.req.PostSearchCond;
import com.community.domain.post.dto.req.PostUpdateReq;
import com.community.domain.post.dto.res.PostRes;
import com.community.domain.post.dto.res.SimplePostRes;
import com.community.domain.post.entity.Post;
import com.community.domain.post.exception.PostNotFoundException;
import com.community.domain.post.repository.PostRepository;
import com.community.domain.user.entity.User;
import com.community.domain.user.service.UserService;
import com.community.global.config.security.dto.SessionUser;
import com.community.global.exception.CannotAccessException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor

public class PostService {

	private final PostMapper mapper;

	private final PostRepository repository;

	private final UserService userService;
	private final PostRepository postRepository;

	public Post findById(Long postId) {
		return repository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());
	}

	public PostRes getSinglePost(Long postId) {
		Post post = findById(postId);
		return mapper.toPostRes(post); // 추가조회됨
	}

	public PostRes createPost(SessionUser sessionUser, PostCreateReq postCreateReq) {
		User user = userService.findById(sessionUser.getId());
		Post post = mapper.toEntity(user, postCreateReq);
		Post savedPost = repository.save(post);
		return mapper.toPostRes(post);
	}

	public void deletePost(SessionUser sessionUser, Long postId) {
		Post post = findById(postId);
		checkAccess(sessionUser, post);
		repository.delete(post);
	}

	private void checkAccess(SessionUser sessionUser, Post post) {
		if (!Objects.equals(sessionUser.getId(), post.getId())) {
			throw new CannotAccessException();
		}
	}

	public Page<SimplePostRes> getPostList(Pageable pageable, PostSearchCond searchCond) {
		Page<Post> posts = postRepository.findPostsBySearchCond(pageable, searchCond);
		return posts.map(mapper::toSimplePostRes);
	}

	@Transactional
	public void updatePost(Long postId, SessionUser sessionUser, PostUpdateReq req) {
		Post post = findById(postId);
		checkAccess(sessionUser, post);
		post.changeTitle(req.title());
		post.changeContent(req.content());
	}
}
