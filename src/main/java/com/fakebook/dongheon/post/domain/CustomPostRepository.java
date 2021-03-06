package com.fakebook.dongheon.post.domain;

import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CustomPostRepository {
	private final PostRepository postRepository;

	public List<Post> findAll() {
		return postRepository.findAll();
	}

	public List<Post> findAllByMember(Member member) {
		return postRepository.findAllByMember(member);
	}

	public Post findById(Long id) {
		return postRepository.findById(id)
				.orElseThrow(PostNotFoundException::new);
	}

	public Post findWithCommentsById(Long id) {
		return postRepository.findWithCommentsById(id)
				.orElseThrow(PostNotFoundException::new);
	}

	public Post save(Post post) {
		return postRepository.save(post);
	}

	public void delete(Post post) {
		postRepository.delete(post);
	}

	public void deleteAll() {
		postRepository.deleteAll();
	}

	public boolean isExistPost(Long id) {
		return postRepository.findAll().stream()
				.anyMatch(post -> post.getId().equals(id));
	}
}
