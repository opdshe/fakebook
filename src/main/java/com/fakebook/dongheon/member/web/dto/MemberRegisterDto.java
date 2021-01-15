package com.fakebook.dongheon.member.web.dto;

import com.fakebook.dongheon.member.domain.Gender;
import com.fakebook.dongheon.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class MemberRegisterDto {
	private String userId;
	private String password;
	private String name;
	private int birthdayYear;
	private int birthdayMonth;
	private int birthdayDay;
	private Gender gender;

	public Member toEntity() {
		return Member.builder()
				.userId(userId)
				.password(password)
				.name(name)
				.birthday(LocalDate.of(birthdayYear, birthdayMonth, birthdayDay))
				.gender(gender)
				.build();
	}
}
