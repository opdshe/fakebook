package com.fakebook.dongheon.post.web.dto;

import com.fakebook.dongheon.comment.web.dto.CommentResponseDto;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.post.domain.Post;
import com.fakebook.dongheon.post.util.PastTimeCalculator;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PostResponseDto {
	private Long id;
	private Long posterId;
	private String content;
	private String poster;
	private String youtubeUrl;
	private String pastTime;
	private int like;
	private boolean hasLiked;
	private List<CommentResponseDto> comments;

	public static PostResponseDto of(Post post, Member loginUser) {
		PostResponseDto dto = new PostResponseDto();
		dto.id = post.getId();
		dto.posterId = post.getMember().getId();
		dto.content = post.getContent();
		dto.poster = post.getMember().getName();
		dto.youtubeUrl = post.getYoutubeUrl();
		dto.like = post.getLike();
		dto.hasLiked = post.getPeopleWhoLikeThis().contains(loginUser);
		dto.pastTime = PastTimeCalculator.getPastTime(post.getPostDateTime());
		dto.comments = post.getComments().stream()
				.map(comment -> CommentResponseDto.of(comment, loginUser))
				.collect(Collectors.toList());
		return dto;
	}
}
