package com.fakebook.dongheon.member.service;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Gender;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.exception.AlreadyExistMemberIdException;
import com.fakebook.dongheon.member.exception.MemberNotFoundException;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
				2021, 1, 15, Gender.MALE);

		//when
		memberService.register(dto);
		Member member = customMemberRepository.findByUserId("testId");

		//then
		assertThat(member).isEqualTo(dto.toEntity());
	}

	@Test
	void 회원_동록시_이미_존재하는_Id이면_예외_발생() {
		//given
		MemberRegisterDto alreadyExistMember = new MemberRegisterDto("duplicatedUserId", "testPw",
				"dongheon", 2021, 1, 15, Gender.MALE);
		MemberRegisterDto duplicatedUserIdMember = new MemberRegisterDto("duplicatedUserId", "1111",
				"lee", 2021, 1, 16, Gender.FEMALE);
		memberService.register(alreadyExistMember);

		//when & then
		assertThatExceptionOfType(AlreadyExistMemberIdException.class)
				.isThrownBy(() -> memberService.register(duplicatedUserIdMember));
	}

	@Test
	void 회원정보_수정_동작_확인() {
		//given
		String userId = "testId";
		String anotherId = "anotherId";
		MemberRegisterDto originMember = new MemberRegisterDto(userId, "testPw", "dongheon",
				2021, 1, 15, Gender.MALE);
		MemberRegisterDto updateMember = new MemberRegisterDto(anotherId, "0000", "lee",
				2021, 1, 16, Gender.FEMALE);

		customMemberRepository.save(originMember.toEntity());
		Long id = customMemberRepository.findByUserId(userId).getId();

		//when
		memberService.update(id, updateMember);
		Member member = customMemberRepository.findById(id);

		//then
		assertThat(member.getUserId()).isEqualTo(anotherId);
	}

	@Test
	void 회원정보_수정시_중복되는_ID는_예외발생() {
		//given
		String duplicatedId = "testId";
		MemberRegisterDto originMember = new MemberRegisterDto(duplicatedId, "testPw", "dongheon",
				2021, 1, 15, Gender.MALE);
		MemberRegisterDto updateMember = new MemberRegisterDto(duplicatedId, "0000", "lee",
				2021, 1, 15, Gender.FEMALE);

		customMemberRepository.save(originMember.toEntity());
		Long id = customMemberRepository.findByUserId(duplicatedId).getId();

		//when & then
		assertThatExceptionOfType(AlreadyExistMemberIdException.class)
				.isThrownBy(() -> memberService.update(id, updateMember));
	}

	@Test
	void 회원_삭제_동작_확인() {
		//given
		String userId = "testID";
		MemberRegisterDto registerDto = new MemberRegisterDto(userId, "testPw", "dongheon",
				2021, 1, 15, Gender.MALE);
		memberService.register(registerDto);
		Long id = customMemberRepository.findByUserId(userId).getId();

		//when
		memberService.delete(id);

		//then
		assertThatExceptionOfType(MemberNotFoundException.class)
				.isThrownBy(() -> customMemberRepository.findById(id));
	}
}