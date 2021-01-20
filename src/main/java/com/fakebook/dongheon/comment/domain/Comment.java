package com.fakebook.dongheon.comment.domain;

import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.post.domain.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {
	@Column(name = "comment_id")
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

	@Column(name = "comment_like")
	private Integer like;

	@ManyToMany
	@JoinTable(name = "comment_like")
	private Set<Member> fans = new HashSet<>();

	public static Comment of(Post post, Member member, String content) {
		return new Comment(post, member, content);
	}

	private Comment(Post post, Member member, String content) {
		this.member = member;
		this.content = content;
		this.like = 0;
		setPost(post);
	}

	public Integer like() {
		like++;
		return like;
	}

	public Integer cancelLike() {
		like--;
		return like;
	}

	private void setPost(Post post) {
		this.post = post;
		post.getComments().add(this);
	}
}
