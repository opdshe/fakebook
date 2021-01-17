package com.fakebook.dongheon.post.web.controller;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import com.fakebook.dongheon.post.domain.CustomPostRepository;
import com.fakebook.dongheon.post.domain.Post;
import com.fakebook.dongheon.post.service.PostService;
import com.fakebook.dongheon.post.web.dto.PostRegisterDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.fakebook.dongheon.member.service.MemberServiceTest.getTestMemberDto;
import static com.fakebook.dongheon.post.service.PostServiceTest.getLoginUserId;
import static com.fakebook.dongheon.post.service.PostServiceTest.getTestPostRegisterDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostApiControllerTest {
	private static final String MY_ACCOUNT_ID = "myAccount";
	private static final String ANOTHER_PERSON_ACCOUNT_ID = "notMyAccount";

	@Autowired
	WebApplicationContext context;

	@Autowired
	private CustomPostRepository customPostRepository;

	@Autowired
	private CustomMemberRepository customMemberRepository;

	@Autowired
	private PostService postService;

	private static MockMvc mvc;

	@BeforeAll
	void init() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();

		MemberRegisterDto memberDto = getTestMemberDto();
		memberDto.setUserId(MY_ACCOUNT_ID);
		Member myAccount = memberDto.toEntity();

		memberDto.setUserId(ANOTHER_PERSON_ACCOUNT_ID);
		Member anotherPersonAccount = memberDto.toEntity();

		customMemberRepository.save(myAccount);
		customMemberRepository.save(anotherPersonAccount);
	}

	@AfterEach
	void deleteAll() {
		customPostRepository.deleteAll();
	}

	@AfterAll
	void clearUp() {
		customMemberRepository.deleteAll();
	}

	@WithMockUser(username = MY_ACCOUNT_ID)
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

	@WithMockUser(username = MY_ACCOUNT_ID)
	@Test
	void Post_삭제_컨트롤러_테스트() throws Exception {
		//given
		PostRegisterDto dto = getTestPostRegisterDto();
		Long postId = postService.register(dto, getLoginUserId());

		//when
		mvc.perform(delete("/post/delete/{id}", postId));
		boolean isExist = customPostRepository.isExistPost(postId);

		//then
		assertThat(isExist).isFalse();
	}

	@WithMockUser(username = MY_ACCOUNT_ID)
	@Test
	void Post_수정_컨트롤러_테스트() throws Exception {
		//given
		PostRegisterDto originDto = getTestPostRegisterDto();
		PostRegisterDto updateDto = getTestPostRegisterDto();
		updateDto.setContent("update Content");

		Long postId = postService.register(originDto, getLoginUserId());

		//when
		mvc.perform(post("/post/update/{id}", postId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(updateDto)));
		Post post = customPostRepository.findById(postId);

		//then
		assertThat(post.getContent()).isEqualTo(updateDto.getContent());
	}
}