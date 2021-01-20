package com.fakebook.dongheon.comment.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	@EntityGraph(attributePaths = {"post", "member"})
	@Override
	Optional<Comment> findById(Long id);

	@EntityGraph(attributePaths = {"post", "member"})
	@Override
	List<Comment> findAll();
}
