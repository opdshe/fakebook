package com.fakebook.dongheon.post.web.controller;

import com.fakebook.dongheon.post.service.PostService;
import com.fakebook.dongheon.post.web.dto.PostRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class PostApiController {
	private final PostService postService;

	@PostMapping("/post/register")
	public Long register(@RequestBody PostRegisterDto dto, Principal principal) {
		String loginUserId = principal.getName();
		return postService.register(dto, loginUserId);
	}
}
