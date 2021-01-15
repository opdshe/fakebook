package com.fakebook.dongheon.member.web.controller;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Gender;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
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

	private static MemberRegisterDto getTestDto() {
		String userID = "testID";
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