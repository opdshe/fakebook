package com.fakebook.dongheon.post.web.dto;

import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.post.domain.Post;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostRegisterDto {
	private String content;

	public Post toEntity(Member member) {
		return new Post(content, member);
	}
}
