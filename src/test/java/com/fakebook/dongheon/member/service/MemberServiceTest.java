package com.fakebook.dongheon.member.service;

import com.fakebook.dongheon.member.domain.Gender;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.domain.MemberRepositoryCustom;
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
	private static final String FRIEND_USER_ID = "friend";

	@Autowired
	private MemberRepositoryCustom memberRepositoryCustom;

	@Autowired
	private CustomPostRepository customPostRepository;

	@Autowired
	private MemberService memberService;

	@BeforeAll
	void setUp() {
		customPostRepository.deleteAll();
		memberRepositoryCustom.deleteAll();
	}

	@AfterEach
	void clearUp() {
		memberRepositoryCustom.deleteAll();
	}

	@Test
	void 회원_등록_동작_확인() {
		//given
		MemberRegisterDto dto = getTestMemberDto();

		//when
		memberService.register(dto);
		Member member = memberRepositoryCustom.findByUserId(dto.getUserId());

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

		memberRepositoryCustom.save(originMember.toEntity());
		Long id = memberRepositoryCustom.findByUserId(originMember.getUserId()).getId();

		//when
		memberService.update(id, updateMember);
		Member member = memberRepositoryCustom.findById(id);

		//then
		assertThat(member.getUserId()).isEqualTo(anotherId);
	}

	@Test
	void 회원정보_수정시_중복되는_ID는_예외발생() {
		//given
		String duplicatedId = MY_USER_ID;
		MemberRegisterDto originMember = getTestMemberDto();
		MemberRegisterDto updateMember = getTestMemberDto();
		memberRepositoryCustom.save(originMember.toEntity());
		Long id = memberRepositoryCustom.findByUserId(duplicatedId).getId();

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
		Long id = memberRepositoryCustom.findByUserId(registerDto.getUserId()).getId();

		//when
		memberService.delete(id);

		//then
		assertThatExceptionOfType(MemberNotFoundException.class)
				.isThrownBy(() -> memberRepositoryCustom.findById(id));
	}

	@WithMockUser(username = "invalidUserID")
	@Test
	public void 삭제하려는_대상이_로그인된_사용자가_아니면_삭제할_수_없다() {
		//given
		MemberRegisterDto registerDto = getTestMemberDto();
		memberService.register(registerDto);
		Long id = memberRepositoryCustom.findByUserId(registerDto.getUserId()).getId();

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
		friendDto.setUserId(FRIEND_USER_ID);
		Long friendMemberId = memberService.register(friendDto);

		//when
		memberService.befriend(friendMemberId, MY_USER_ID);
		Member myAccount = memberRepositoryCustom.findWithFriendsById(myMemberId);
		Member friendAccount = memberRepositoryCustom.findWithFriendsById(friendMemberId);

		//then
		assertThat(myAccount.getFriends()).contains(friendAccount);
		assertThat(friendAccount.getFriends()).contains(myAccount);
	}

	@WithMockUser(username = MY_USER_ID)
	@Test
	void 친구삭제_동작_확인() {
		//given
		MemberRegisterDto myAccountDto = getTestMemberDto();
		Long myMemberId = memberService.register(myAccountDto);

		MemberRegisterDto friendDto = getTestMemberDto();
		friendDto.setUserId(FRIEND_USER_ID);
		Long friendMemberId = memberService.register(friendDto);

		memberService.befriend(friendMemberId, MY_USER_ID);

		//when
		memberService.unfriend(friendMemberId, MY_USER_ID);
		Member myAccount = memberRepositoryCustom.findWithFriendsById(myMemberId);
		Member friendAccount = memberRepositoryCustom.findWithFriendsById(friendMemberId);

		//then
		assertThat(myAccount.getFriends()).doesNotContain(friendAccount);
		assertThat(friendAccount.getFriends()).doesNotContain(myAccount);
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