package com.fakebook.dongheon.comment.service;

import com.fakebook.dongheon.comment.domain.Comment;
import com.fakebook.dongheon.comment.domain.CustomCommentRepository;
import com.fakebook.dongheon.comment.web.dto.CommentRegisterDto;
import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.post.domain.CustomPostRepository;
import com.fakebook.dongheon.post.domain.Post;
import com.fakebook.dongheon.security.exception.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CustomCommentRepository customCommentRepository;
	private final CustomMemberRepository customMemberRepository;
	private final CustomPostRepository customPostRepository;

	@Transactional
	public Long register(CommentRegisterDto dto, Long postId, String loginUserId) {
		Member loginUser = customMemberRepository.findByUserId(loginUserId);
		Post post = customPostRepository.findWithCommentsById(postId);
		Comment comment = dto.toEntity(post, loginUser);
		return customCommentRepository.save(comment).getId();
	}

	@Transactional
	public void delete(Long commentId, String loginUserId) {
		Member loginUser = customMemberRepository.findByUserId(loginUserId);
		Comment comment = customCommentRepository.findById(commentId);
		validateAuthority(loginUser, comment);
		customCommentRepository.delete(comment);
	}

	@Transactional
	public Integer like(Long commentId, String loginUserId) {
		Member member = customMemberRepository.findByUserId(loginUserId);
		Comment comment = customCommentRepository.findById(commentId);
		return member.likeComment(comment);
	}

	private static void validateAuthority(Member loginUser, Comment comment) {
		if (!loginUser.equals(comment.getMember())) {
			throw new NotAuthorizedException();
		}
	}
}
