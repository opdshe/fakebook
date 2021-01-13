package com.fakebook.dongheon.member.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {
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

	@Builder
	public Member(String userId, String password, String name, LocalDate birthday, Gender gender) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
	}
}
