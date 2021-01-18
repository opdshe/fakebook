package com.fakebook.dongheon.post.web.dto;

import com.fakebook.dongheon.post.domain.Post;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PostResponseDto {
	private final Long id;
	private final String content;

	public PostResponseDto(Long id, String content) {
		this.id = id;
		this.content = content;
	}

	public static PostResponseDto of(Post post) {
		return new PostResponseDto(post.getId(), post.getContent());
	}
}
