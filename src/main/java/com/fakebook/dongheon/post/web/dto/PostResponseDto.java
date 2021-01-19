package com.fakebook.dongheon.post.web.dto;

import com.fakebook.dongheon.post.domain.Post;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PostResponseDto {
	private final Long id;
	private final String content;
	private final String poster;

	public PostResponseDto(Long id, String content, String poster) {
		this.id = id;
		this.content = content;
		this.poster = poster;
	}

	public static PostResponseDto of(Post post) {
		return new PostResponseDto(post.getId(), post.getContent(), post.getMember().getName());
	}
}
