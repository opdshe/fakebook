package com.fakebook.dongheon.post.domain;

import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.post.web.dto.PostRegisterDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(exclude = "id")
@Getter
@NoArgsConstructor
@Entity
public class Post {
	@Column(name = "post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(name = "content")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	public Post(String content, Member member) {
		this.content = content;
		this.member = member;
	}

	public void update(PostRegisterDto dto) {
		this.content = dto.getContent();
	}
}
