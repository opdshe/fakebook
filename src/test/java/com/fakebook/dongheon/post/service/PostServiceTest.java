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
		Member myAccount = memberDto.toEntity();

		memberDto.setUserId(ANOTHER_PERSON_ACCOUNT_ID);
		Member anotherPersonAccount = memberDto.toEntity();

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
				.isThrownBy(() -> postService.delete(id, getLoginUserId()));
	}

	@WithMockUser(username = MY_ACCOUNT_ID)
	@Test
	void Post_수정_동작_확인() {
		//given
		PostRegisterDto originDto = getTestPostRegisterDto();
		PostRegisterDto updateDto = getTestPostRegisterDto();
		updateDto.setContent("update Content");

		Long postId = postService.register(originDto, getLoginUserId());

		//when
		postService.update(postId, updateDto, getLoginUserId());
		Post post = customPostRepository.findById(postId);

		//then
		assertThat(post.getContent()).isEqualTo(updateDto.getContent());
	}

	@WithMockUser(username = MY_ACCOUNT_ID)
	@Test
	void 수정하려는_게시글의_작성자가_수정을_요청한_유저가_아니면_삭제_할_수_없다() {
		//given
		String realPostWriterId = ANOTHER_PERSON_ACCOUNT_ID;
		PostRegisterDto originDto = getTestPostRegisterDto();
		PostRegisterDto updateDto = getTestPostRegisterDto();
		updateDto.setContent("update Content");

		Long id = postService.register(originDto, realPostWriterId);

		//when & then
		assertThatExceptionOfType(NotAuthorizedException.class)
				.isThrownBy(() -> postService.update(id, updateDto, getLoginUserId()));
	}

	@WithMockUser(username = MY_ACCOUNT_ID)
	@Test
	void 좋아요_기능_동작_확인() {
		//given
		PostRegisterDto postDto = getTestPostRegisterDto();
		String loginUserId = getLoginUserId();
		Long postId = postService.register(postDto, loginUserId);

		//when
		System.out.println(postId);
		Post post = customPostRepository.findById(postId);
		Member member = customMemberRepository.findByUserId(loginUserId);
		int like = postService.like(postId, loginUserId);
	}

	public static String getLoginUserId() {
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