package com.fakebook.dongheon.member.domain;

import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@EqualsAndHashCode(exclude = {"id", "password"})
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

	public void update(MemberRegisterDto dto) {
		this.userId = dto.getUserId();
		this.password = dto.getPassword();
		this.name = dto.getName();
		this.birthday = LocalDate.of(dto.getBirthdayYear(), dto.getBirthdayMonth(), dto.getBirthdayDay());
		this.gender = dto.getGender();
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
