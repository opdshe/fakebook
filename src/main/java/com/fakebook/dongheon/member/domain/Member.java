package com.fakebook.dongheon.member.domain;

import com.fakebook.dongheon.JpaBaseEntity;
import com.fakebook.dongheon.comment.domain.Comment;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import com.fakebook.dongheon.post.domain.Post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@EqualsAndHashCode(exclude = {"id", "password"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends JpaBaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "birthday", nullable = false)
	private LocalDate birthday;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender", nullable = false)
	private Gender gender;

	public void update(MemberRegisterDto dto) {
		this.userId = dto.getUserId();
		this.password = dto.getPassword();
		this.name = dto.getName();
		this.birthday = LocalDate.of(dto.getBirthdayYear(), dto.getBirthdayMonth(), dto.getBirthdayDay());
		this.gender = dto.getGender();
	}

	public int likeComment(Comment comment) {
		if (hasAlreadyLiked(comment)) {
			comment.getPeopleWhoLikeThis().remove(this);
			return comment.cancelLike();
		}
		comment.getPeopleWhoLikeThis().add(this);
		return comment.like();
	}

	public int likePost(Post post) {
		if (hasAlreadyLiked(post)) {
			post.getPeopleWhoLikeThis().remove(this);
			return post.cancelLike();
		}
		post.getPeopleWhoLikeThis().add(this);
		return post.like();
	}

	private boolean hasAlreadyLiked(Comment comment) {
		return comment.getPeopleWhoLikeThis().contains(this);
	}

	private boolean hasAlreadyLiked(Post post) {
		return post.getPeopleWhoLikeThis().contains(this);
	}

	@Builder
	public Member(String userId, String password, String name, LocalDate birthday, Gender gender) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
	}
}
