package com.fakebook.dongheon.post.web.controller;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.post.domain.CustomPostRepository;
import com.fakebook.dongheon.post.web.dto.PostRegisterDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.fakebook.dongheon.member.service.MemberServiceTest.getTestMemberDto;
import static com.fakebook.dongheon.post.service.PostServiceTest.getTestPostRegisterDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostApiControllerTest {
	private static final String USER_ID = "testId";

	@Autowired
	WebApplicationContext context;

	@Autowired
	private CustomPostRepository customPostRepository;

	@Autowired
	private CustomMemberRepository customMemberRepository;

	@LocalServerPort
	private int port;

	private static MockMvc mvc;

	@BeforeAll
	void init() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();

		Member testMember = getTestMemberDto().toEntity();
		customMemberRepository.save(testMember);
	}

	@AfterEach
	void deleteAll() {
		customPostRepository.deleteAll();
	}

	@AfterAll
	void clearUp() {
		customMemberRepository.deleteAll();
	}

	@WithMockUser(username = USER_ID)
	@Test
	void Post_등록_컨트롤러_테스트() throws Exception {
		//given
		PostRegisterDto dto = getTestPostRegisterDto();

		//when
		MvcResult result = mvc.perform(post("/post/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dto)))
				.andReturn();
		long id = Long.parseLong(result.getResponse().getContentAsString());
		boolean isExistPost = customPostRepository.isExistPost(id);

		//then
		assertThat(isExistPost).isTrue();
	}
}