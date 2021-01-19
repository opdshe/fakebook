package com.fakebook.dongheon.post.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
	@Override
	@EntityGraph(attributePaths = {"member"})
	List<Post> findAll();

	@Override
	@EntityGraph(attributePaths = {"member"})
	Optional<Post> findById(Long id);

	@EntityGraph(attributePaths = {"member", "comments"})
	Optional<Post> findWithCommentsById(Long id);
}
