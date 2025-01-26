package com.community.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.community.domain.post.dto.req.PostSearchCond;
import com.community.domain.post.entity.Post;

public interface PostRepositoryCustom {
	Page<Post> findPostsBySearchCond(Pageable pageable, PostSearchCond postSearchCond);

}
