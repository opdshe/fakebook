package com.fakebook.dongheon.member.web.controller;

import com.fakebook.dongheon.member.service.MemberService;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/member")
@RequiredArgsConstructor
@RestController
public class MemberApiController {
	private final MemberService memberService;

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
