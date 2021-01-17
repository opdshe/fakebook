package com.fakebook.dongheon.post.service;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.post.domain.CustomPostRepository;
import com.fakebook.dongheon.post.domain.Post;
import com.fakebook.dongheon.post.web.dto.PostRegisterDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import static com.fakebook.dongheon.member.service.MemberServiceTest.getTestMemberDto;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class PostServiceTest {
	private static final String USER_ID = "testId";

	@Autowired
	private CustomMemberRepository customMemberRepository;

	@Autowired
	private CustomPostRepository customPostRepository;

	@Autowired
	private PostService postService;

	@BeforeAll
	void initMemberRepository() {
		Member testMember = getTestMemberDto().toEntity();
		customMemberRepository.save(testMember);
	}

	@AfterAll
	void deleteMemberRepository() {
		customMemberRepository.deleteAll();
	}

	@AfterEach
	void clearUp() {
		customPostRepository.deleteAll();
	}

	@WithMockUser(username = USER_ID)
	@Test
	void Post_등록_동작_확인() {
		//given
		PostRegisterDto dto = getTestPostRegisterDto();
		String loginUserId = getLoginUserId();

		//when
		Long postId = postService.register(dto, loginUserId);
		Post post = customPostRepository.findById(postId);

		//then
		assertThat(post.getContent()).isEqualTo(dto.getContent());
	}

	private String getLoginUserId() {
		UserDetails loginUser = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal();
		return loginUser.getUsername();
	}

	public static PostRegisterDto getTestPostRegisterDto() {
		PostRegisterDto dto = new PostRegisterDto();
		dto.setContent("test content");
		return dto;
	}
}