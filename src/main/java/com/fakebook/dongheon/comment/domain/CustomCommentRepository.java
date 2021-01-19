package com.fakebook.dongheon.comment.domain;

import com.fakebook.dongheon.comment.exception.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomCommentRepository {
	private final CommentRepository commentRepository;

	public Comment findById(Long id) {
		return commentRepository.findById(id)
				.orElseThrow(CommentNotFoundException::new);
	}

	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	public void delete(Comment comment) {
		commentRepository.delete(comment);
	}

	public void deleteAll() {
		commentRepository.deleteAll();
	}
}
