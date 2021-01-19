package com.fakebook.dongheon.comment.domain;

import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.post.domain.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(name = "content")
	private String content;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	public static Comment of(Post post, Member member, String content) {
		return new Comment(post, member, content);
	}

	private Comment(Post post, Member member, String content) {
		this.member = member;
		this.content = content;
		setPost(post);
	}

	private void setPost(Post post) {
		this.post = post;
		post.getComments().add(this);
	}
}
