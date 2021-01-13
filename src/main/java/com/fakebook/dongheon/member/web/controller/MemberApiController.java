package com.fakebook.dongheon.member.web.controller;

import com.fakebook.dongheon.member.service.MemberService;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberApiController {
	private final MemberService memberService;

	public MemberApiController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("/register")
	public Long register(@RequestBody MemberRegisterDto dto) {
		return memberService.register(dto);
	}

	@PostMapping("/update")
	public void update(Long id, @RequestBody MemberRegisterDto dto) {
		memberService.update(id, dto);
	}

	@DeleteMapping("/delete")
	public void delete(Long id) {
		memberService.delete(id);
	}
}
