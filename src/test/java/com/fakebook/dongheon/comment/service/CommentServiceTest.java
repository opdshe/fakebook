package com.fakebook.dongheon.comment.service;

import com.fakebook.dongheon.comment.domain.Comment;
import com.fakebook.dongheon.comment.domain.CustomCommentRepository;
import com.fakebook.dongheon.comment.web.dto.CommentRegisterDto;
import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import com.fakebook.dongheon.post.domain.CustomPostRepository;
import com.fakebook.dongheon.post.domain.Post;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static com.fakebook.dongheon.member.service.MemberServiceTest.getTestMemberDto;
import static com.fakebook.dongheon.post.service.PostServiceTest.getLoginUserId;
import static com.fakebook.dongheon.post.service.PostServiceTest.getTestPostRegisterDto;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class CommentServiceTest {
	private static final String MY_ACCOUNT_ID = "myAccount";
	private static final String ANOTHER_PERSON_ACCOUNT_ID = "yourAccount";
	private static final String COMMENT_DTO_CONTENT = "test content";

	private static Long testPostId;

	@Autowired
	private CustomMemberRepository customMemberRepository;

	@Autowired
	private CustomPostRepository customPostRepository;

	@Autowired
	private CustomCommentRepository customCommentRepository;

	@Autowired
	private CommentService commentService;

	@BeforeAll
	void initMemberAndPost() {
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

	@WithMockUser(MY_ACCOUNT_ID)
	@Test
	void 댓글_작성_기능_동작_확인() {
		//given
		CommentRegisterDto commentDto = getCommentRegisterDto();
		String loginUserID = getLoginUserId();

		//when
		Long commentId = commentService.register(commentDto, testPostId, loginUserID);
		Comment comment = customCommentRepository.findById(commentId);

		//then
		assertThat(comment.getContent()).isEqualTo(commentDto.getContent());
	}

	public static CommentRegisterDto getCommentRegisterDto() {
		CommentRegisterDto dto = new CommentRegisterDto();
		dto.setContent(COMMENT_DTO_CONTENT);
		return dto;
	}
}