package com.fakebook.dongheon.comment.web.dto;

import com.fakebook.dongheon.comment.domain.Comment;
import com.fakebook.dongheon.member.domain.Member;
import lombok.Getter;

@Getter
public class CommentResponseDto {
	private Long id;
	private String content;
	private String commenter;
	private Long commenterId;
	private Integer like;
	private boolean hasLiked;

	public static CommentResponseDto of(Comment comment, Member loginUser) {
		CommentResponseDto dto = new CommentResponseDto();
		dto.id = comment.getId();
		dto.content = comment.getContent();
		dto.commenter = comment.getMember().getName();
		dto.commenterId = comment.getMember().getId();
		dto.like = comment.getLike();
		dto.hasLiked = comment.getPeopleWhoLikeThis().contains(loginUser);
		return dto;
	}
}
