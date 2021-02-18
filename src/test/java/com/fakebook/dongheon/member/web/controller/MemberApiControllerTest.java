package com.fakebook.dongheon.member.web.controller;

import com.fakebook.dongheon.member.domain.Gender;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.domain.MemberRepositoryCustom;
import com.fakebook.dongheon.member.service.MemberService;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import com.fakebook.dongheon.post.domain.CustomPostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberApiControllerTest {
	private static final String MY_USER_ID = "myAccount";
	private static final String FRIEND_USER_ID = "friend";
	private static final String REGISTER_URL = "/member/register";
	private static final String DELETE_URL = "/member/delete";
	private static final String UPDATE_URL = "/member/update";

	@Autowired
	WebApplicationContext context;

	@Autowired
	private MemberRepositoryCustom memberRepositoryCustom;

	@Autowired
	private CustomPostRepository customPostRepository;

	@Autowired
	private MemberService memberService;

	private static MockMvc mvc;

	@BeforeAll
	public void setUp() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
		customPostRepository.deleteAll();
		memberRepositoryCustom.deleteAll();
	}

	@AfterEach
	public void clearUp() {
		memberRepositoryCustom.deleteAll();
	}

	@Test
	void Member_등록_기능_컨트롤러_테스트() throws Exception {
		//given
		MemberRegisterDto dto = getTestDto();
		String memberJson = new ObjectMapper().writeValueAsString(dto);
		String url = "/member/register";

		//when
		mvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(memberJson));
		boolean isExistUserId = memberRepositoryCustom.isExistUserId(dto.getUserId());

		//then
		assertThat(isExistUserId).isTrue();
	}

	@WithMockUser(username = MY_USER_ID)
	@Test
	void Member_삭제_기능_컨트롤러_테스트() throws Exception {
		//given
		MemberRegisterDto dto = getTestDto();
		String memberJson = new ObjectMapper().writeValueAsString(dto);
		MvcResult result = mvc.perform(post(REGISTER_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(memberJson))
				.andReturn();

		long id = Long.parseLong(result.getResponse().getContentAsString());

		//when
		mvc.perform(delete(DELETE_URL + "/{id}", id));
		boolean isExistUserId = memberRepositoryCustom.isExistUserId(dto.getUserId());

		//then
		assertThat(isExistUserId).isFalse();
	}

	@Test
	void Member_수정_기능_컨트롤러_테스트() throws Exception {
		//given
		MemberRegisterDto origin = getTestDto();
		MemberRegisterDto editedDto = getTestDto();
		String editedUserId = "editedUserID";
		editedDto.setUserId(editedUserId);

		MvcResult result = mvc.perform(post(REGISTER_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(origin)))
				.andReturn();
		long id = Long.parseLong(result.getResponse().getContentAsString());

		//when
		mvc.perform(post(UPDATE_URL + "/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(editedDto)));
		Member editedMember = memberRepositoryCustom.findById(id);

		//then
		assertThat(editedMember.getUserId()).isEqualTo(editedUserId);
	}

	@WithMockUser(username = MY_USER_ID)
	@Test
	void 친구추가_컨트롤러_테스트() throws Exception {
		//given
		MemberRegisterDto myAccountDto = getTestDto();
		Long myMemberId = memberService.register(myAccountDto);

		MemberRegisterDto friendAccountDto = getTestDto();
		friendAccountDto.setUserId(FRIEND_USER_ID);
		Long friendMemberId = memberService.register(friendAccountDto);

		//when
		mvc.perform(post("/member/friend/{id}", friendMemberId));
		Member myAccount = memberRepositoryCustom.findWithFriendsById(myMemberId);
		Member friendAccount = memberRepositoryCustom.findWithFriendsById(friendMemberId);

		//then
		assertThat(myAccount.getFriends()).contains(friendAccount);
		assertThat(friendAccount.getFriends()).contains(myAccount);
	}

	@WithMockUser(username = MY_USER_ID)
	@Test
	void 친구삭제_컨트롤러_테스트() throws Exception {
		//given
		MemberRegisterDto myAccountDto = getTestDto();
		Long myMemberId = memberService.register(myAccountDto);

		MemberRegisterDto friendAccountDto = getTestDto();
		friendAccountDto.setUserId(FRIEND_USER_ID);
		Long friendMemberId = memberService.register(friendAccountDto);

		memberService.befriend(friendMemberId, MY_USER_ID);

		//when
		mvc.perform(delete("/member/friend/{id}", friendMemberId));
		Member myAccount = memberRepositoryCustom.findWithFriendsById(myMemberId);
		Member friendAccount = memberRepositoryCustom.findWithFriendsById(friendMemberId);

		//then
		assertThat(myAccount.getFriends()).doesNotContain(friendAccount);
		assertThat(friendAccount.getFriends()).doesNotContain(myAccount);
	}

	private static MemberRegisterDto getTestDto() {
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