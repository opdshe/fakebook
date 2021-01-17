package com.fakebook.dongheon.post.domain;

import com.fakebook.dongheon.JpaBaseEntity;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.post.web.dto.PostRegisterDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(exclude = "id", callSuper = false)
@Getter
@NoArgsConstructor
@Entity
public class Post extends JpaBaseEntity {
	@Column(name = "post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(name = "content")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(name = "post_date")
	private LocalDateTime postDate;

	public Post(String content, Member member) {
		this.content = content;
		this.member = member;
		this.postDate = LocalDateTime.now();
	}

	public void update(PostRegisterDto dto) {
		this.content = dto.getContent();
	}
}
