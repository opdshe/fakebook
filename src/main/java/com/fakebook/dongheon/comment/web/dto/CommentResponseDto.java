package com.fakebook.dongheon.comment.web.dto;

import com.fakebook.dongheon.comment.domain.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
	private Long id;
	private String content;
	private String commenter;

	public static CommentResponseDto of(Comment comment) {
		CommentResponseDto dto = new CommentResponseDto();
		dto.id = comment.getId();
		dto.content = comment.getContent();
		dto.commenter = comment.getMember().getName();
		return dto;
	}
}
