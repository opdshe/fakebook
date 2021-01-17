package com.fakebook.dongheon.post.domain;

import com.fakebook.dongheon.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomPostRepository {
	private final PostRepository postRepository;

	public Post findById(Long id) {
		return postRepository.findById(id)
				.orElseThrow(PostNotFoundException::new);
	}

	public Post save(Post post) {
		return postRepository.save(post);
	}

	public void deleteAll() {
		postRepository.deleteAll();
	}

	public boolean isExistPost(Long id) {
		return postRepository.findAll().stream()
				.anyMatch(post -> post.getId().equals(id));
	}
}
