package com.fakebook.dongheon.member.service;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Gender;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceTest {
	@Autowired
	private CustomMemberRepository memberRepository;

	@Autowired
	private MemberService memberService;

	@Test
	void 회원_등록_동작_확인() {
		//given
		MemberRegisterDto dto = new MemberRegisterDto("testId", "testPw", "dongheon",
				LocalDate.now(), Gender.MALE);

		//when
		memberService.register(dto);
		Member member = memberRepository.findByUserId("testId");

		//then
		assertThat(member).isEqualTo(dto.toEntity());
	}
}