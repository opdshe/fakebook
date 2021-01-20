package com.fakebook.dongheon.comment.web;

import com.fakebook.dongheon.comment.service.CommentService;
import com.fakebook.dongheon.comment.web.dto.CommentRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/comment")
@RequiredArgsConstructor
@RestController
public class CommentApiController {
	private final CommentService commentService;

	@PostMapping("/register/{postId}")
	public Long register(@RequestBody CommentRegisterDto dto, @PathVariable Long postId, Principal principal) {
		String loginUserId = principal.getName();
		return commentService.register(dto, postId, loginUserId);
	}

	@DeleteMapping("/delete/{commentId}")
	public void delete(@PathVariable Long commentId, Principal principal) {
		String loginUserId = principal.getName();
		commentService.delete(commentId, loginUserId);
	}

	@PostMapping("/like/{commentId}")
	public int like(@PathVariable Long commentId, Principal principal) {
		String loginUserID = principal.getName();
		return commentService.like(commentId, loginUserID);
	}
}
