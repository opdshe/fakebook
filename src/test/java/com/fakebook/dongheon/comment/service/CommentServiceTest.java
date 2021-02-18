package com.fakebook.dongheon.comment.service;

import com.fakebook.dongheon.comment.domain.Comment;
import com.fakebook.dongheon.comment.domain.CustomCommentRepository;
import com.fakebook.dongheon.comment.exception.CommentNotFoundException;
import com.fakebook.dongheon.comment.web.dto.CommentRegisterDto;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.domain.MemberRepositoryCustom;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import com.fakebook.dongheon.post.domain.CustomPostRepository;
import com.fakebook.dongheon.post.domain.Post;
import com.fakebook.dongheon.security.exception.NotAuthorizedException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static com.fakebook.dongheon.member.service.MemberServiceTest.getTestMemberDto;
import static com.fakebook.dongheon.post.service.PostServiceTest.getLoginUserId;
import static com.fakebook.dongheon.post.service.PostServiceTest.getTestPostRegisterDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class CommentServiceTest {
	private static final String MY_ACCOUNT_ID = "myAccount";
	private static final String ANOTHER_PERSON_ACCOUNT_ID = "yourAccount";
	private static final String COMMENT_DTO_CONTENT = "test content";

	private static Long testPostId;

	@Autowired
	private MemberRepositoryCustom memberRepositoryCustom;

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

		memberRepositoryCustom.save(myAccount);
		memberRepositoryCustom.save(anotherPersonAccount);

		Post post = getTestPostRegisterDto().toEntity(myAccount);
		testPostId = customPostRepository.save(post).getId();
	}

	@AfterAll
	void deleteMemberAndPost() {
		customPostRepository.deleteAll();
		memberRepositoryCustom.deleteAll();
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

	@WithMockUser(MY_ACCOUNT_ID)
	@Test
	void 댓글_삭제_기능_동작_확인() {
		//given
		CommentRegisterDto commentDto = getCommentRegisterDto();
		String loginUserId = getLoginUserId();
		Long commentId = commentService.register(commentDto, testPostId, loginUserId);

		//when
		commentService.delete(commentId, loginUserId);

		assertThatExceptionOfType(CommentNotFoundException.class)
				.isThrownBy(() -> customCommentRepository.findById(commentId));
	}

	@WithMockUser(ANOTHER_PERSON_ACCOUNT_ID)
	@Test
	void 자신이_작성한_댓글이아니면_삭제할_수_없다() {
		//given
		CommentRegisterDto commentDto = getCommentRegisterDto();
		Long commentId = commentService.register(commentDto, testPostId, MY_ACCOUNT_ID);
		String anotherPersonId = getLoginUserId();


		//when
		assertThatExceptionOfType(NotAuthorizedException.class)
				.isThrownBy(() -> commentService.delete(commentId, anotherPersonId));
	}

	@WithMockUser(MY_ACCOUNT_ID)
	@Test
	void 좋아요_기능_동작_확인() {
		//given
		String loginUserId = getLoginUserId();
		CommentRegisterDto commentDto = getCommentRegisterDto();
		Long commentId = commentService.register(commentDto, testPostId, MY_ACCOUNT_ID);

		//when
		int like = commentService.like(commentId, loginUserId);

		//then
		assertThat(like).isEqualTo(1);
	}

	public static CommentRegisterDto getCommentRegisterDto() {
		CommentRegisterDto dto = new CommentRegisterDto();
		dto.setContent(COMMENT_DTO_CONTENT);
		return dto;
	}
}