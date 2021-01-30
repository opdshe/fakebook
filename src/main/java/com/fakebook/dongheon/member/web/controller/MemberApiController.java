package com.fakebook.dongheon.member.web.controller;

import com.fakebook.dongheon.member.service.MemberService;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import com.fakebook.dongheon.member.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/member")
@RequiredArgsConstructor
@RestController
public class MemberApiController {
	private final MemberService memberService;

	@PostMapping("/register")
	public Long register(@RequestBody MemberRegisterDto dto) {
		return memberService.register(dto);
	}

	@PostMapping("/update/{id}")
	public void update(@PathVariable Long id, @RequestBody MemberRegisterDto dto) {
		memberService.update(id, dto);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		memberService.delete(id);
	}

	@PostMapping("/friend/{id}")
	public void befriend(@PathVariable Long id, Principal principal) {
		String loginUserId = principal.getName();
		memberService.befriend(id, loginUserId);
	}

	@DeleteMapping("/friend/{id}")
	public void unfriend(@PathVariable Long id, Principal principal) {
		String loginUserId = principal.getName();
		memberService.unfriend(id, loginUserId);
	}

	@GetMapping("/{id}")
	public MemberResponseDto getMemberById(@PathVariable Long id) {
		return memberService.getMemberById(id);
	}
}
