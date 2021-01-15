package com.fakebook.dongheon.member.web.dto;

import com.fakebook.dongheon.member.domain.Gender;
import com.fakebook.dongheon.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MemberRegisterDto {
	private final String userId;
	private final String password;
	private final String name;
	private final int birthdayYear;
	private final int birthdayMonth;
	private final int birthdayDay;
	private final Gender gender;

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
