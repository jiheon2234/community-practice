package com.community.domain.reply.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.community.domain.reply.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

	@Query("""
		SELECT r FROM Reply r WHERE r.post.id = :postId AND r.path > :lastSeenPath
		ORDER BY  r.path ASC
		""")
	List<Reply> getRepliesByPostIdAndLastSeenPath(Long postId, String lastSeenPath, Pageable pageable);
}
