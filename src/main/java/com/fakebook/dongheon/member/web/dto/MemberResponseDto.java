package com.fakebook.dongheon.member.web.dto;

import com.fakebook.dongheon.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDate;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MemberResponseDto {
	private Long id;
	private String name;
	private String userId;
	private LocalDate birthday;


	public static MemberResponseDto of(Member member) {
		MemberResponseDto dto = new MemberResponseDto();
		dto.id = member.getId();
		dto.name = member.getName();
		dto.userId = member.getUserId();
		dto.birthday = member.getBirthday();
		return dto;
	}
}
