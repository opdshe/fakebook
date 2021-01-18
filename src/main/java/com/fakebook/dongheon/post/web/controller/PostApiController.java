package com.fakebook.dongheon.post.web.controller;

import com.fakebook.dongheon.post.service.PostService;
import com.fakebook.dongheon.post.web.dto.PostRegisterDto;
import com.fakebook.dongheon.post.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {
	private final PostService postService;

	@PostMapping("/post/register")
	public Long register(@RequestBody PostRegisterDto dto, Principal principal) {
		String loginUserId = principal.getName();
		return postService.register(dto, loginUserId);
	}

	@DeleteMapping("/post/delete/{id}")
	public void delete(@PathVariable Long id, Principal principal) {
		String loginUserId = principal.getName();
		postService.delete(id, loginUserId);
	}

	@PostMapping("/post/update/{id}")
	public void update(@PathVariable Long id, @RequestBody PostRegisterDto dto, Principal principal) {
		String loginUserId = principal.getName();
		postService.update(id, dto, loginUserId);
	}

	@GetMapping("/posts")
	public List<PostResponseDto> findAllSortByPostDate() {
		return postService.findAllSortByPostDate();
	}
}
