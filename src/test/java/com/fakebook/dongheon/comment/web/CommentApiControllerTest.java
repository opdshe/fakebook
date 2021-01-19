package com.fakebook.dongheon.comment.web;

import com.fakebook.dongheon.comment.domain.Comment;
import com.fakebook.dongheon.comment.domain.CustomCommentRepository;
import com.fakebook.dongheon.comment.exception.CommentNotFoundException;
import com.fakebook.dongheon.comment.service.CommentService;
import com.fakebook.dongheon.comment.web.dto.CommentRegisterDto;
import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import com.fakebook.dongheon.post.domain.CustomPostRepository;
import com.fakebook.dongheon.post.domain.Post;
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

import static com.fakebook.dongheon.comment.service.CommentServiceTest.getCommentRegisterDto;
import static com.fakebook.dongheon.member.service.MemberServiceTest.getTestMemberDto;
import static com.fakebook.dongheon.post.service.PostServiceTest.getLoginUserId;
import static com.fakebook.dongheon.post.service.PostServiceTest.getTestPostRegisterDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentApiControllerTest {
	private static final String MY_ACCOUNT_ID = "myAccount";
	private static final String ANOTHER_PERSON_ACCOUNT_ID = "notMyAccount";

	private static Long testPostId;

	@Autowired
	WebApplicationContext context;

	@Autowired
	private CustomPostRepository customPostRepository;

	@Autowired
	private CustomMemberRepository customMemberRepository;

	@Autowired
	private CustomCommentRepository customCommentRepository;

	@Autowired
	private CommentService commentService;

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
		Post post = getTestPostRegisterDto().toEntity(myAccount);
		testPostId = customPostRepository.save(post).getId();
	}

	@AfterAll
	void deleteMemberAndPost() {
		customPostRepository.deleteAll();
		customMemberRepository.deleteAll();
	}

	@AfterEach
	void clearUp() {
		customCommentRepository.deleteAll();
	}

	@WithMockUser(username = MY_ACCOUNT_ID)
	@Test
	void 댓글_등록_컨트롤러_테스트() throws Exception {
		//given
		CommentRegisterDto dto = getCommentRegisterDto();

		//when
		MvcResult result = mvc.perform(post("/comment/register/{postId}", testPostId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dto)))
				.andReturn();
		Long commentId = Long.parseLong(result.getResponse().getContentAsString());
		Comment comment = customCommentRepository.findById(commentId);

		//then
		assertThat(dto.getContent()).isEqualTo(comment.getContent());
	}

	@WithMockUser(username = MY_ACCOUNT_ID)
	@Test
	void 댓글_삭제_컨트롤러_테스트() throws Exception {
		//given
		CommentRegisterDto dto = getCommentRegisterDto();
		String loginUserId = getLoginUserId();
		Long commentId = commentService.register(dto, testPostId, loginUserId);

		//when
		mvc.perform(delete("/comment/delete/{commentId}", commentId));

		//then
		assertThatExceptionOfType(CommentNotFoundException.class)
				.isThrownBy(() -> customCommentRepository.findById(commentId));
	}
}