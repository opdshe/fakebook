package com.fakebook.dongheon.comment.web.dto;

import com.fakebook.dongheon.comment.domain.Comment;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.post.domain.Post;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRegisterDto {
	private String content;

	public Comment toEntity(Post post, Member member) {
		return Comment.of(post, member, content);
	}
}
