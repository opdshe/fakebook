package com.fakebook.dongheon.post.web.dto;

import com.fakebook.dongheon.comment.web.dto.CommentResponseDto;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.post.domain.Post;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PostResponseDto {
	private Long id;
	private String content;
	private String poster;
	private String youtubeUrl;
	private List<CommentResponseDto> comments;
	private boolean hasLiked;
	private int like;

	public static PostResponseDto of(Post post, Member loginUser) {
		PostResponseDto dto = new PostResponseDto();
		dto.id = post.getId();
		dto.content = post.getContent();
		dto.poster = post.getMember().getName();
		dto.comments = post.getComments().stream()
				.map(comment -> CommentResponseDto.of(comment, loginUser))
				.collect(Collectors.toList());
		dto.hasLiked = post.getPeopleWhoLikeThis().contains(loginUser);
		dto.like = post.getLike();
		dto.youtubeUrl = post.getYoutubeUrl();
		return dto;
	}
}
