package com.fakebook.dongheon.post.service;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import com.fakebook.dongheon.post.domain.CustomPostRepository;
import com.fakebook.dongheon.post.domain.Post;
import com.fakebook.dongheon.post.web.dto.PostRegisterDto;
import com.fakebook.dongheon.security.exception.NotAuthorizedException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import static com.fakebook.dongheon.member.service.MemberServiceTest.getTestMemberDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class PostServiceTest {
	private static final String MY_ACCOUNT_ID = "myAccount";
	private static final String ANOTHER_PERSON_ACCOUNT_ID = "yourAccount";

	private static Member myAccount;

	private static Member anotherPersonAccount;

	@Autowired
	private CustomMemberRepository customMemberRepository;

	@Autowired
	private CustomPostRepository customPostRepository;

	@Autowired
	private PostService postService;

	@BeforeAll
	void initMemberRepository() {
		MemberRegisterDto memberDto = getTestMemberDto();
		memberDto.setUserId(MY_ACCOUNT_ID);
		myAccount = memberDto.toEntity();

		memberDto.setUserId(ANOTHER_PERSON_ACCOUNT_ID);
		anotherPersonAccount = memberDto.toEntity();

		customMemberRepository.save(myAccount);
		customMemberRepository.save(anotherPersonAccount);
	}

	@AfterAll
	void deleteMemberRepository() {
		customMemberRepository.deleteAll();
	}

	@AfterEach
	void clearUp() {
		customPostRepository.deleteAll();
	}

	@WithMockUser(username = MY_ACCOUNT_ID)
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

	@WithMockUser(username = MY_ACCOUNT_ID)
	@Test
	void Post_삭제_동작_확인() {
		//given
		String loginUserID = getLoginUserId();
		PostRegisterDto dto = getTestPostRegisterDto();
		Long id = postService.register(dto, loginUserID);

		//when
		postService.delete(id, loginUserID);
		boolean isExist = customPostRepository.isExistPost(id);

		//then
		assertThat(isExist).isFalse();
	}

	@WithMockUser(username = MY_ACCOUNT_ID)
	@Test
	void 삭제하려는_게시글의_작성자가_삭제요청한_유저가_아니면_삭제_할_수_없다() {
		//given
		String realPostWriterId = ANOTHER_PERSON_ACCOUNT_ID;
		PostRegisterDto dto = getTestPostRegisterDto();
		Long id = postService.register(dto, realPostWriterId);

		//when & then
		assertThatExceptionOfType(NotAuthorizedException.class)
				.isThrownBy(() -> postService.delete(id, MY_ACCOUNT_ID));
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