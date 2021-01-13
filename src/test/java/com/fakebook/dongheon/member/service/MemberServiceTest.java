package com.fakebook.dongheon.member.service;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Gender;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.exception.AlreadyExistMemberException;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class MemberServiceTest {
	@Autowired
	private CustomMemberRepository customMemberRepository;

	@Autowired
	private MemberService memberService;

	@AfterEach
	void clearUp() {
		customMemberRepository.deleteAll();
	}

	@Test
	void 회원_등록_동작_확인() {
		//given
		MemberRegisterDto dto = new MemberRegisterDto("testId", "testPw", "dongheon",
				LocalDate.now(), Gender.MALE);

		//when
		memberService.register(dto);
		Member member = customMemberRepository.findByUserId("testId");

		//then
		assertThat(member).isEqualTo(dto.toEntity());
	}

	@Test
	void 이미존재하는_Id이면_예외_발생() {
		//given
		MemberRegisterDto alreadyExistMember = new MemberRegisterDto("duplicatedUserId", "testPw",
				"dongheon", LocalDate.now(), Gender.MALE);
		MemberRegisterDto duplicatedUserIdMember = new MemberRegisterDto("duplicatedUserId", "1111",
				"lee", LocalDate.now(), Gender.FEMALE);
		memberService.register(alreadyExistMember);

		//when & then
		assertThatExceptionOfType(AlreadyExistMemberException.class)
				.isThrownBy(() -> memberService.register(duplicatedUserIdMember));
	}
}