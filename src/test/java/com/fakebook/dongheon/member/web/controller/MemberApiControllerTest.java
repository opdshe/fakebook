package com.fakebook.dongheon.member.web.controller;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Gender;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberApiControllerTest {
	@Autowired
	WebApplicationContext context;

	@Autowired
	private CustomMemberRepository customMemberRepository;

	@LocalServerPort
	private int port;

	private static MockMvc mvc;

	private static final String userId = "testID";

	@BeforeAll
	public void setUp() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}

	@AfterEach
	public void clearUp() {
		customMemberRepository.deleteAll();
	}

	@Test
	void Member_등록_기능_컨트롤러_테스트() throws Exception {
		//given
		MemberRegisterDto dto = getTestDto();
		String memberJson = new ObjectMapper().writeValueAsString(dto);
		String url = "http://localhost:" + port + "/member/register";

		//when
		mvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(memberJson));
		boolean isExistUserId = customMemberRepository.isExistUserId(dto.getUserId());

		//then
		assertThat(isExistUserId).isTrue();
	}

	@WithMockUser(username = userId)
	@Test
	void Member_삭제_기능_컨트롤러_테스트() throws Exception {
		//given
		MemberRegisterDto dto = getTestDto();
		String memberJson = new ObjectMapper().writeValueAsString(dto);
		String registerUrl = "http://localhost:" + port + "/member/register";
		String deleteUrl = "http://localhost:" + port + "/member/delete";
		MvcResult result = mvc.perform(post(registerUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(memberJson))
				.andReturn();

		long id = Long.parseLong(result.getResponse().getContentAsString());

		//when
		mvc.perform(delete(deleteUrl + "/{id}", id));
		boolean isExistUserId = customMemberRepository.isExistUserId(dto.getUserId());

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

		String registerUrl = "http://localhost:" + port + "/member/register";
		String updateUrl = "http://localhost:" + port + "/member/update";
		MvcResult result = mvc.perform(post(registerUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(origin)))
				.andReturn();
		long id = Long.parseLong(result.getResponse().getContentAsString());

		//when
		mvc.perform(post(updateUrl + "/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(editedDto)));
		Member editedMember = customMemberRepository.findById(id);

		//then
		assertThat(editedMember.getUserId()).isEqualTo(editedUserId);
	}

	private static MemberRegisterDto getTestDto() {
		String userID = userId;
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