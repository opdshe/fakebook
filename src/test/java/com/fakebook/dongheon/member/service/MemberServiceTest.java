package com.fakebook.dongheon.member.service;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Gender;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.exception.AlreadyExistMemberIdException;
import com.fakebook.dongheon.member.exception.MemberNotFoundException;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import com.fakebook.dongheon.post.domain.CustomPostRepository;
import com.fakebook.dongheon.security.exception.NotAuthorizedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class MemberServiceTest {
	private static final String MY_USER_ID = "dongheon";

	@Autowired
	private CustomMemberRepository customMemberRepository;

	@Autowired
	private CustomPostRepository customPostRepository;

	@Autowired
	private MemberService memberService;

	@BeforeAll
	void setUp() {
		customPostRepository.deleteAll();
		customMemberRepository.deleteAll();
	}

	@AfterEach
	void clearUp() {
		customMemberRepository.deleteAll();
	}

	@Test
	void 회원_등록_동작_확인() {
		//given
		MemberRegisterDto dto = getTestMemberDto();

		//when
		memberService.register(dto);
		Member member = customMemberRepository.findByUserId(dto.getUserId());

		//then
		assertThat(member).isEqualTo(dto.toEntity());
	}

	@Test
	void 회원_동록시_이미_존재하는_Id이면_예외_발생() {
		//given
		MemberRegisterDto alreadyExistMember = getTestMemberDto();
		MemberRegisterDto duplicatedUserIdMember = getTestMemberDto();
		memberService.register(alreadyExistMember);

		//when & then
		assertThatExceptionOfType(AlreadyExistMemberIdException.class)
				.isThrownBy(() -> memberService.register(duplicatedUserIdMember));
	}

	@Test
	void 회원정보_수정_동작_확인() {
		//given
		String anotherId = "anotherId";
		MemberRegisterDto originMember = getTestMemberDto();
		MemberRegisterDto updateMember = getTestMemberDto();
		updateMember.setUserId(anotherId);

		customMemberRepository.save(originMember.toEntity());
		Long id = customMemberRepository.findByUserId(originMember.getUserId()).getId();

		//when
		memberService.update(id, updateMember);
		Member member = customMemberRepository.findById(id);

		//then
		assertThat(member.getUserId()).isEqualTo(anotherId);
	}

	@Test
	void 회원정보_수정시_중복되는_ID는_예외발생() {
		//given
		String duplicatedId = MY_USER_ID;
		MemberRegisterDto originMember = getTestMemberDto();
		MemberRegisterDto updateMember = getTestMemberDto();
		customMemberRepository.save(originMember.toEntity());
		Long id = customMemberRepository.findByUserId(duplicatedId).getId();

		//when & then
		assertThatExceptionOfType(AlreadyExistMemberIdException.class)
				.isThrownBy(() -> memberService.update(id, updateMember));
	}

	@WithMockUser(username = MY_USER_ID)
	@Test
	void 회원_삭제_동작_확인() {
		//given
		MemberRegisterDto registerDto = getTestMemberDto();
		memberService.register(registerDto);
		Long id = customMemberRepository.findByUserId(registerDto.getUserId()).getId();

		//when
		memberService.delete(id);

		//then
		assertThatExceptionOfType(MemberNotFoundException.class)
				.isThrownBy(() -> customMemberRepository.findById(id));
	}

	@WithMockUser(username = "invalidUserID")
	@Test
	public void 삭제하려는_대상이_로그인된_사용자가_아니면_삭제할_수_없다() {
		//given
		MemberRegisterDto registerDto = getTestMemberDto();
		memberService.register(registerDto);
		Long id = customMemberRepository.findByUserId(registerDto.getUserId()).getId();

		//when & then
		assertThatExceptionOfType(NotAuthorizedException.class)
				.isThrownBy(() -> memberService.delete(id));
	}

	@WithMockUser(username = MY_USER_ID)
	@Test
	void 친구추가_동작_확인() {
		//given
		MemberRegisterDto myAccountDto = getTestMemberDto();
		Long myMemberId = memberService.register(myAccountDto);

		MemberRegisterDto friendDto = getTestMemberDto();
		friendDto.setUserId("friendId");
		Long friendMemberId = memberService.register(friendDto);

		//when
		memberService.beFriend(friendMemberId, MY_USER_ID);
		Member myAccount = customMemberRepository.findWithFriendsById(myMemberId);
		Member friendAccount = customMemberRepository.findWithFriendsById(friendMemberId);

		//then
		assertThat(myAccount.getFriends()).contains(friendAccount);
		assertThat(friendAccount.getFriends()).contains(myAccount);
	}

	public static MemberRegisterDto getTestMemberDto() {
		String userID = MY_USER_ID;
		String password = "testPW";
		String name = "이동헌";
		int birthdayYear = 1995;
		int birthdayMonth = 8;
		int birthdayDay = 22;
		Gender gender = Gender.MALE;
		return new MemberRegisterDto(userID, password, name, birthdayYear, birthdayMonth,
				birthdayDay, gender);
	}
}